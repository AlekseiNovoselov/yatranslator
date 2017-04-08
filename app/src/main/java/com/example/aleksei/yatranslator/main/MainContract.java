package com.example.aleksei.yatranslator.main;

import com.example.aleksei.yatranslator.BasePresenter;
import com.example.aleksei.yatranslator.BaseView;

/**
 * Created by aleksei on 08.04.17.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setResult(String text);
    }

    interface Presenter extends BasePresenter {
        void translate(CharSequence text);
    }
}
