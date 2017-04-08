package com.example.aleksei.yatranslator.data.network.processor;

import android.content.Context;

import com.example.aleksei.yatranslator.data.network.Request;
import com.example.aleksei.yatranslator.data.network.Resource;
import com.example.aleksei.yatranslator.data.network.Response;
import com.example.aleksei.yatranslator.data.network.TranslationResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TranslateProcessor extends Processor {

    public static final String KEY_TRANSLATION_RESULT = "KEY_TRANSLATION_RESULT";
    private String mText;

    public TranslateProcessor(Context context, Processor.OnProcessorResultListener listener, String text) {
        super(context, listener);
        mText = text;
    }

    @Override
    protected Response parseResponseJSONObject(JSONObject responseJSONObject) {
        int status = responseJSONObject.optInt("code");
        Resource result;
        // TODO здесь идёт парсинг ответа от сервера, который потом игнорируется
        String message;
        switch (status) {
            case Response.RESULT_OK:
                result = TranslationResult.fromJSONObject(responseJSONObject);
                break;
            case Response.RESULT_CANNOT_SEND_MESSAGE:
                message = "Cannot send auth message";
                result = new ErrorMessage(message);
                break;
            case Response.RESULT_UNAUTHORIZED:
                message = responseJSONObject.optString("message");
                result = new ErrorMessage(message);
                break;
            default:
                message = responseJSONObject.optString("message");
                result = new ErrorMessage(message);
                break;
        }
        Map<String, Resource> data = new HashMap<>();
        data.put(KEY_TRANSLATION_RESULT, result);
        return new Response(status, data);
    }

    @Override
    protected Request prepareRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "trnsl.1.1.20170408T190248Z.75ab9656097b1b4f.7c9f6b9f5472ceb5e978f9cfbe34d492fdce8625");
        params.put("text", mText);
        params.put("lang", "en-ru");
        params.put("format", "html");
        String path = "api/v1.5/tr.json/translate";
        return new Request(mContext, path, Request.RequestMethod.POST, params);
    }

    @Override
    protected void updateDataBeforeExecutingRequest(Request request) {

    }

    @Override
    protected void updateDataAfterExecutingRequest(Response response) {

    }
}
