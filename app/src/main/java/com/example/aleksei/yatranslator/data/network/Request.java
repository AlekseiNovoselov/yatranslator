package com.example.aleksei.yatranslator.data.network;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.aleksei.yatranslator.R;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

public class Request {
    private static final String LOG_TAG = Request.class.getSimpleName();

    private final String mUrlRoot;
    private final String mPath;
    private final RequestMethod mRequestMethod;
    private final Map<String, String> mParams;
    private OkHttpClient client;

    public Request(Context context, String path, RequestMethod requestMethod, Map<String, String> params) {
        mUrlRoot = context.getString(R.string.url_root);
        mPath = path;
        mRequestMethod = requestMethod;
        mParams = params;
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private String runGet(String url) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String runPost(String url, FormBody formBody) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Nullable
    public JSONObject execute() {
        JSONObject responseJSONObject;
        switch (mRequestMethod) {
            case GET:
                responseJSONObject =  doGet(mPath, mParams);
                break;
            case POST:
                responseJSONObject = doPost(mPath, mParams);
                break;
            default:
                responseJSONObject = null;
        }
        return responseJSONObject;
    }


    private JSONObject doGet(String path, Map<String, String> params) {
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        StringBuilder paramsQuery = new StringBuilder();
        JSONObject responseJSONObject;
        JSONObject unknownErrorJSONObject = new JSONObject();
        try {
            unknownErrorJSONObject.put("code", Response.RESULT_UNEXPECTED_ERROR);

            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                paramsQuery.append(entry.getKey()).append("=").append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
                if (iterator.hasNext()) {
                    paramsQuery.append("&");
                }
            }

            URL url = new URL(mUrlRoot + path + "?" + paramsQuery.toString());
            String responseString = runGet(url.toString());
            responseJSONObject = new JSONObject(responseString);

        } catch (ConnectException e) {
            try {
                responseJSONObject = new JSONObject("{code: " + Response.RESULT_CANNOT_SEND_MESSAGE + '}');
            } catch (JSONException e1) {
                responseJSONObject = unknownErrorJSONObject;
                Log.w(LOG_TAG, e1);
            }
        } catch (JSONException | IOException e) {
            try {
                unknownErrorJSONObject.put("message", e.getMessage());
            } catch (JSONException e1) {
                Log.w(LOG_TAG, e1);
            }
            responseJSONObject = unknownErrorJSONObject;
            Log.w(LOG_TAG, e);
        }
        return responseJSONObject;
    }

    private JSONObject doPost(String path, Map<String, String> formData) {
        Iterator<Map.Entry<String, String>> iterator = formData.entrySet().iterator();
        JSONObject response;
        JSONObject unknownErrorJSONObject = new JSONObject();
        try {
            unknownErrorJSONObject.put("code", Response.RESULT_UNEXPECTED_ERROR);

            FormBody.Builder formBuilder = new FormBody.Builder();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                formBuilder.add(entry.getKey().toString(), entry.getValue().toString());
            }
            URL url = new URL(mUrlRoot + path);
            String responseString = runPost(url.toString(), formBuilder.build());
            response = new JSONObject(responseString);

        } catch (ConnectException e) {
            try {
                response = new JSONObject("{code: " + Response.RESULT_CANNOT_SEND_MESSAGE + '}');
            } catch (JSONException e1) {
                response = unknownErrorJSONObject;
                Log.w(LOG_TAG, e1);
            }
        } catch (JSONException | IOException e) {
            try {
                unknownErrorJSONObject.put("message", e.getMessage());
            } catch (JSONException e1) {
                Log.w(LOG_TAG, e1);
            }
            response = unknownErrorJSONObject;
            Log.w(LOG_TAG, e);
        }
        return response;
    }


    public enum RequestMethod {
        GET,
        POST
    }
}
