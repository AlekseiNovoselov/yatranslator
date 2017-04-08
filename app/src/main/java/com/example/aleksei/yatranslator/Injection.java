package com.example.aleksei.yatranslator;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.aleksei.yatranslator.data.local.LocalDataSource;
import com.example.aleksei.yatranslator.data.network.RemoteDataSource;
import com.example.aleksei.yatranslator.data.Repository;

public class Injection {

    public static Repository provideRepository(@NonNull Context context) {
        return Repository.getInstance(RemoteDataSource.getInstance(context),
                LocalDataSource.getInstance(context));
    }
}
