package com.example.aleksei.yatranslator.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aleksei.yatranslator.R;

import org.w3c.dom.Text;

public class MainFragment extends Fragment implements MainContract.View {

    private static final String LAST_TRANSLATE = "LAST_TRANSLATE";
    private MainContract.Presenter mPresenter;

    private TextView mResultText;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);

        mResultText = (TextView) root.findViewById(R.id.resultText);

        EditText editText = (EditText) root.findViewById(R.id.input);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    mPresenter.translate(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return root;
    }

    public void setResult(String text) {
        mResultText.setText(text);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop(getContext());
    }

}
