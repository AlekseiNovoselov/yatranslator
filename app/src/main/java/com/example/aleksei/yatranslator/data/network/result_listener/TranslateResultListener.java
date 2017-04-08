package com.example.aleksei.yatranslator.data.network.result_listener;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.aleksei.yatranslator.data.network.Response;
import com.example.aleksei.yatranslator.data.network.TranslatorNetworkService;
import com.example.aleksei.yatranslator.data.network.TranslationResult;
import com.example.aleksei.yatranslator.data.network.processor.ErrorMessage;
import com.example.aleksei.yatranslator.data.network.processor.Processor;
import com.example.aleksei.yatranslator.data.network.processor.TranslateProcessor;

public class TranslateResultListener implements Processor.OnProcessorResultListener {

    private final Intent mOriginalRequestIntent;
    private final ResultReceiver mResultReceiver;
    public TranslateResultListener(Intent originalRequestIntent, ResultReceiver resultReceiver) {
        mOriginalRequestIntent = originalRequestIntent;
        mResultReceiver = resultReceiver;
    }

    @Override
    public void send(@Nullable Response response) {
        if (response == null)
            return;

        if (mResultReceiver != null) {
            Bundle result = null;

            switch (response.getStatus()) {
                case Response.RESULT_OK:
                    result = handleResultOk(response);
                    break;
                case Response.RESULT_UNEXPECTED_ERROR:
                    result = handleResultUnavailable();
                    break;
                case Response.RESULT_UNAUTHORIZED:
                    result = handleErrorMessage(response);
                    break;
                default:
                    result = handleErrorMessage(response);
            }

            if (result == null)
                result = new Bundle();

            mResultReceiver.send(response.getStatus(), result);
        }
    }

    @Nullable
    private Bundle handleErrorMessage(@NonNull Response response) {
        Bundle result = new Bundle();
        ErrorMessage errorMessage = (ErrorMessage) response.getData().get(TranslateProcessor.KEY_TRANSLATION_RESULT);
        result.putString(TranslatorNetworkService.ERROR_MESSAGE, errorMessage.getmMessage());
        result.putParcelable(TranslatorNetworkService.EXTRA_ORIGINAL_INTENT, mOriginalRequestIntent);
        return result;
    }

    @NonNull
    private Bundle handleResultUnavailable() {
        Bundle result = new Bundle();
        result.putParcelable(TranslatorNetworkService.EXTRA_ORIGINAL_INTENT, mOriginalRequestIntent);
        return result;
    }

    @Nullable
    private Bundle handleResultOk(@NonNull Response response) {
        TranslationResult traslationResult =
                (TranslationResult) response.getData().get(TranslateProcessor.KEY_TRANSLATION_RESULT);

        if (traslationResult == null)
            return null;

        Bundle result = new Bundle();
        result.putString(TranslatorNetworkService.EXTRA_TEXT, traslationResult.getResult());
//        result.putString(BughouseNetworkService.EXTRA_USERNAME, authenticationResult.getUsername());
//        result.putString(BughouseNetworkService.EXTRA_SESSION_ID, authenticationResult.getSessionId());
//        result.putString(BughouseNetworkService.EXTRA_USER_ID, authenticationResult.getUserId());
//        result.putString(BughouseNetworkService.EXTRA_USER_SCORE, authenticationResult.getScore());
//        result.putString(BughouseNetworkService.EXTRA_USER_PHOTO_URI, authenticationResult.getPhotoUri());
        result.putParcelable(TranslatorNetworkService.EXTRA_ORIGINAL_INTENT, mOriginalRequestIntent);
        return result;
    }
}
