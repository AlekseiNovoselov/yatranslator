package com.example.aleksei.yatranslator.data.network.processor;

import android.content.Context;

import com.example.aleksei.yatranslator.data.network.Request;
import com.example.aleksei.yatranslator.data.network.Response;

import org.json.JSONObject;

public abstract class Processor {
    protected final Context mContext;
    protected final OnProcessorResultListener mListener;

    protected Processor(Context context, OnProcessorResultListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void process() {
        try {
            Request request = prepareRequest();
            updateDataBeforeExecutingRequest(request);
            JSONObject responseJSONObject = request.execute();
            Response response = parseResponseJSONObject(responseJSONObject);
            if (response.getStatus() == 200) {
                updateDataAfterExecutingRequest(response);
            }
            mListener.send(response);
        } catch (Exception e) {
            mListener.send(new Response(0, null));
        }
    }

    abstract protected Response parseResponseJSONObject(JSONObject responseJSONObject);

    abstract protected Request prepareRequest();

    abstract protected void updateDataBeforeExecutingRequest(Request request);

    abstract protected void updateDataAfterExecutingRequest(Response response);

    public interface OnProcessorResultListener {
        void send(Response response);
    }
}
