package com.example.aleksei.yatranslator.data.network;

import java.util.Map;

public class Response {
    public static final int RESULT_UNEXPECTED_ERROR = 0;
    public static final int RESULT_CANNOT_SEND_MESSAGE = 1;

    public static final int RESULT_OK = 200;
    public static final int RESULT_UNAUTHORIZED = 401;
    public static final int RESULT_FORBIDDEN = 403;
    public static final int RESULT_INTERNAL_SERVER_ERROR = 500;
    public static final int RESULT_EMAIL_EXISTS = 701;
    public static final int RESULT_REQUIRED_FIELD_IS_NULL = 704;

    private final int mStatus;
    private final Map<String, Resource> mData;

    public Response(int status, Map<String, Resource> data) {
        mStatus = status;
        mData = data;
    }

    public Map<String, Resource> getData() {
        return mData;
    }

    public int getStatus() {
        return mStatus;
    }
}
