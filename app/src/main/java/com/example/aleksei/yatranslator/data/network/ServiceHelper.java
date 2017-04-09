package com.example.aleksei.yatranslator.data.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.aleksei.yatranslator.data.Repository;
import com.example.aleksei.yatranslator.data.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceHelper {

    private static final String TRANSLATE = "TRANSLATE";
    public static final String ACTION_REQUEST_RESULT = "ACTION_REQUEST_RESULT";

    private static ServiceHelper instance;
    private final Context mContext;

    private Map<String, Long> mPendingRequests = new HashMap<>();
    private AtomicLong mRequestIdGenerator = new AtomicLong();

    private ServiceHelper(Context context) {
        mContext = context.getApplicationContext();
    }

    public static ServiceHelper get(Context context) {
        if (instance == null)
            instance = new ServiceHelper(context);
        return instance;
    }

    public long translate(Task task, Repository.RemoteLoadListener callback) {
        long requestId;
        if (mPendingRequests.containsKey(TRANSLATE)) {
            requestId = mPendingRequests.get(TRANSLATE);
        } else {
            requestId = mRequestIdGenerator.incrementAndGet();
            mPendingRequests.put(TRANSLATE, requestId);
            ResultReceiver serviceCallback = new TranslationReceiver(null, TRANSLATE, callback);
            TranslatorNetworkService.startTranslationService(mContext, serviceCallback, requestId, task);
        }
        return requestId;
    }

    private class TranslationReceiver extends ResultReceiver {

        private final String mResource;
        private final Repository.RemoteLoadListener mCallback;

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public TranslationReceiver(Handler handler, String resource, Repository.RemoteLoadListener callback) {
            super(handler);
            mResource = resource;
            mCallback = callback;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("ServiceHelper", "onReceiveResult, resultCode=" + resultCode);
            Intent originalRequestIntent = resultData.getParcelable(TranslatorNetworkService.EXTRA_ORIGINAL_INTENT);
            if (originalRequestIntent != null) {
                long requestId = originalRequestIntent.getLongExtra(TranslatorNetworkService.EXTRA_REQUEST_ID, 0);
                Intent result = new Intent(ACTION_REQUEST_RESULT);

                switch (resultCode) {
                    case Response.RESULT_OK:

                        final String id = resultData.getString(TranslatorNetworkService.EXTRA_ENTITY_ID);
                        final String sourceText = resultData.getString(TranslatorNetworkService.EXTRA_SOURCE_TEXT);
                        final String resultText = resultData.getString(TranslatorNetworkService.EXTRA_RESULT_TEXT);
                        final String sourceLang = resultData.getString(TranslatorNetworkService.EXTRA_SOURCE_LANG);
                        final String resultLang = resultData.getString(TranslatorNetworkService.EXTRA_RESULT_LANG);

                        result.putExtra(TranslatorNetworkService.EXTRA_REQUEST_ID, requestId);
                        result.putExtra(TranslatorNetworkService.EXTRA_RESULT_CODE, resultCode);

                        result.putExtra(TranslatorNetworkService.EXTRA_ENTITY_ID, id);
                        result.putExtra(TranslatorNetworkService.EXTRA_SOURCE_TEXT, sourceText);
                        result.putExtra(TranslatorNetworkService.EXTRA_RESULT_TEXT, resultText);
                        result.putExtra(TranslatorNetworkService.EXTRA_SOURCE_LANG, sourceLang);
                        result.putExtra(TranslatorNetworkService.EXTRA_RESULT_LANG, resultLang);

                        mCallback.onLoaded(new Task(id, sourceText, resultText, sourceLang, resultLang));
                        break;
                    case Response.RESULT_UNEXPECTED_ERROR:
                        break;
                    case Response.RESULT_CANNOT_SEND_MESSAGE:
                    default:
                        break;
                }
                mPendingRequests.remove(mResource);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(result);
            }
        }
    }
}
