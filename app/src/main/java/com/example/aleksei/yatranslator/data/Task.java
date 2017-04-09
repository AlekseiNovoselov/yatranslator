package com.example.aleksei.yatranslator.data;

import android.support.annotation.NonNull;

import com.example.aleksei.yatranslator.data.network.Resource;

import java.util.UUID;

public class Task implements Resource {

    private final String mId;
    private String mResultText;
    private String mSourceText;
    private String mSourceLang;
    private String mResultLang;

    public Task(String sourceText) {
        this(UUID.randomUUID().toString(), sourceText, null, null, null);
    }

    public Task(String sourceText, String sourceLang, String resultLang) {
        this(UUID.randomUUID().toString(), sourceText, null, sourceLang, resultLang);
    }

    public Task(@NonNull String id, String sourceText, String resultText, String sourceLang, String resultLang) {
        mId = id;
        mSourceText = sourceText;
        mResultText = resultText;
        mSourceLang = sourceLang;
        mResultLang = resultLang;
    }

    public String getResultText() {
        return mResultText;
    }

    public String getId() {
        return mId;
    }

    public String getSourceText() {
        return mSourceText;
    }

    public String getSourceLang() {
        return mSourceLang;
    }

    public String getResultLang() {
        return mResultLang;
    }

    public void setResultText(String resultText) {
        mResultText = resultText;
    }
}
