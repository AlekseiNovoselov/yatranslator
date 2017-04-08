package com.example.aleksei.yatranslator.data.network.processor;

import com.example.aleksei.yatranslator.data.network.Resource;

public class ErrorMessage implements Resource {
    private final String mMessage;

    public ErrorMessage (String _message) {
        mMessage = _message;
    }

    public String getmMessage() {
        return mMessage;
    }
}
