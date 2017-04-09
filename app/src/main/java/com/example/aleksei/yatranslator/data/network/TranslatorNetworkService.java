package com.example.aleksei.yatranslator.data.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.aleksei.yatranslator.data.Task;
import com.example.aleksei.yatranslator.data.network.processor.Processor;
import com.example.aleksei.yatranslator.data.network.processor.TranslateProcessor;
import com.example.aleksei.yatranslator.data.network.result_listener.TranslateResultListener;

public class TranslatorNetworkService extends IntentService {

    public static final String EXTRA_ORIGINAL_INTENT = "com.example.aleksei.yatranslator.EXTRA_ORIGINAL_INTENT";
    public static final String EXTRA_REQUEST_ID = "com.example.aleksei.yatranslator.EXTRA_REQUEST_ID";

    public static final String EXTRA_ENTITY_ID = "com.example.aleksei.yatranslator.EXTRA_ENTITY_ID";
    public static final String EXTRA_SOURCE_TEXT = "com.example.aleksei.yatranslator.EXTRA_SOURCE_TEXT";
    public static final String EXTRA_RESULT_TEXT = "com.example.aleksei.yatranslator.EXTRA_RESULT_TEXT";
    public static final String EXTRA_SOURCE_LANG = "com.example.aleksei.yatranslator.EXTRA_SOURCE_LANG";
    public static final String EXTRA_RESULT_LANG = "com.example.aleksei.yatranslator.EEXTRA_RESULT_LANG";

    public static final String ERROR_MESSAGE = "com.example.aleksei.yatranslator.ERROR_MESSAGE";
    public static final String ACTION_TRANSLATE = "com.example.aleksei.yatranslator.ACTION_TRANSLATE";
    private static final String EXTRA_SERVICE_CALLBACK = "EXTRA_SERVICE_CALLBACK";
    private static final String LOG_TAG = TranslatorNetworkService.class.getSimpleName();
    public static final String EXTRA_RESULT_CODE = "com.example.aleksei.yatranslator.EXTRA_RESULT_CODE";

    public TranslatorNetworkService() {
        super("TranslatorNetworkService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final ResultReceiver resultReceiver = intent.getParcelableExtra(EXTRA_SERVICE_CALLBACK);
        final String action = intent.getAction();
        Log.d(LOG_TAG, "action=" + action);
        switch (action) {
            case ACTION_TRANSLATE:
                final String id = intent.getStringExtra(EXTRA_ENTITY_ID);
                final String sourceText = intent.getStringExtra(EXTRA_SOURCE_TEXT);
                final String resultText = intent.getStringExtra(EXTRA_RESULT_TEXT);
                final String sourceLang = intent.getStringExtra(EXTRA_SOURCE_LANG);
                final String resultLang = intent.getStringExtra(EXTRA_RESULT_LANG);
                Task task = new Task(id, sourceText, resultText, sourceLang, resultLang);
                Processor socialSingInProcessor = new TranslateProcessor(this, new TranslateResultListener(intent, resultReceiver), task);
                socialSingInProcessor.process();
        }
    }

    public static void startTranslationService(Context context, ResultReceiver serviceCallback,
                                               long requestId, Task task) {
        Intent intent = new Intent(context, TranslatorNetworkService.class);
        intent.setAction(ACTION_TRANSLATE);
        intent.putExtra(EXTRA_SERVICE_CALLBACK, serviceCallback);
        intent.putExtra(EXTRA_REQUEST_ID, requestId);

        intent.putExtra(EXTRA_ENTITY_ID, task.getId());
        intent.putExtra(EXTRA_SOURCE_TEXT, task.getSourceText());
        intent.putExtra(EXTRA_RESULT_TEXT, task.getResultText());
        intent.putExtra(EXTRA_SOURCE_LANG, task.getSourceLang());
        intent.putExtra(EXTRA_RESULT_LANG, task.getResultLang());

        context.startService(intent);
    }
}
