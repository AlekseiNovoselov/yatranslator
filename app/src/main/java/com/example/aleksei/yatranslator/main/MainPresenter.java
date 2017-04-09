package com.example.aleksei.yatranslator.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.example.aleksei.yatranslator.data.Repository;
import com.example.aleksei.yatranslator.data.Task;
import com.example.aleksei.yatranslator.data.network.Response;
import com.example.aleksei.yatranslator.data.network.ServiceHelper;
import com.example.aleksei.yatranslator.data.network.TranslatorNetworkService;

public class MainPresenter implements MainContract.Presenter {

    private BroadcastReceiver mTranslationCompletedReceiver = new TranslationCompletedReceiver();
    private long mTranslationRequestId;

    private final Repository mRepository;
    private final MainContract.View mMainView;
    private Task lastTask;

    public MainPresenter(@NonNull Repository repository, @NonNull MainContract.View mainView) {
        mRepository = repository;
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void translate(CharSequence text) {
        Task task = new Task(String.valueOf(text), "en", "ru");
        mTranslationRequestId = mRepository.getTranslation(task);
    }

    public void onStart(Context context) {
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(mTranslationCompletedReceiver, new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT));
    }

    public void onStop(Context context) {
        LocalBroadcastManager.getInstance(context)
                .unregisterReceiver(mTranslationCompletedReceiver);
    }

    private class TranslationCompletedReceiver extends BroadcastReceiver {
        private final String LOG_TAG = TranslationCompletedReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            final long requestId = intent.getLongExtra(TranslatorNetworkService.EXTRA_REQUEST_ID, 0);
            final int status = intent.getIntExtra(TranslatorNetworkService.EXTRA_RESULT_CODE, 0);
            if (mTranslationRequestId == requestId) {
                switch (status) {
                    case Response.RESULT_OK:
                        handleResultOk(context, intent);
                        break;
                    case Response.RESULT_CANNOT_SEND_MESSAGE:
                        //handleCanNotSendMessage();
                        break;
                    case Response.RESULT_REQUIRED_FIELD_IS_NULL:
                        //handleResultRequiredFieldsIsNull();
                        break;
                    case Response.RESULT_UNEXPECTED_ERROR:
                        //Snackbar.make(findViewById(R.id.root_view), R.string.internal_auth_client_error_text, Snackbar.LENGTH_LONG).show();
                    default:
                        // TODO в тост выводится ошибка, а не обрабатывается
                        //handleErrorMessage(intent);
                }
            }
            dismissProgressDialog();
        }
    }

    private void handleResultOk(Context context, Intent intent) {
        final String id = intent.getStringExtra(TranslatorNetworkService.EXTRA_ENTITY_ID);
        final String sourceText = intent.getStringExtra(TranslatorNetworkService.EXTRA_SOURCE_TEXT);
        final String resultText = intent.getStringExtra(TranslatorNetworkService.EXTRA_RESULT_TEXT);
        final String sourceLang = intent.getStringExtra(TranslatorNetworkService.EXTRA_SOURCE_LANG);
        final String resultLang = intent.getStringExtra(TranslatorNetworkService.EXTRA_RESULT_LANG);
        lastTask = new Task(id, sourceText, resultText, sourceLang, resultLang);
        mMainView.setResult(resultText);
    }

    private void dismissProgressDialog() {

    }
}
