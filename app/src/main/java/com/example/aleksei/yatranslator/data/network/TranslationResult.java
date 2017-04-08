package com.example.aleksei.yatranslator.data.network;

import org.json.JSONObject;

/**
 * Created by aleksei on 08.04.17.
 */

public class TranslationResult implements Resource {

    private String mResult;

    public TranslationResult(String result) {
        mResult = result;
    }

    public String getResult() {
        return mResult;
    }

    public static TranslationResult fromJSONObject(JSONObject responseJSONObject) {
        final String result = responseJSONObject.optString("text");
        return new TranslationResult(result);
    }
}
