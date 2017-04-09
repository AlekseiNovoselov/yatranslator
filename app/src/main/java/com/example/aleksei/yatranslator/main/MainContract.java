package com.example.aleksei.yatranslator.main;

import android.content.Context;

import com.example.aleksei.yatranslator.BasePresenter;
import com.example.aleksei.yatranslator.BaseView;

import java.io.Serializable;

/**
 * Created by aleksei on 08.04.17.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void setResult(String text);
    }

    interface Presenter extends BasePresenter {
        void translate(CharSequence text);
        void onStart(Context context);
        void onStop(Context context);
    }
}
