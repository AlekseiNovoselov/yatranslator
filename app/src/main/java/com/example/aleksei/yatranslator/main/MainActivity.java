package com.example.aleksei.yatranslator.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.aleksei.yatranslator.Injection;
import com.example.aleksei.yatranslator.note.NoteFragment;
import com.example.aleksei.yatranslator.R;
import com.example.aleksei.yatranslator.settings.SettingsFragment;
import com.example.aleksei.yatranslator.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private MainPresenter mMainPresenter;
    private ActivityUtils.Screen currentScreen;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ActivityUtils.Screen nextScreen = null;
            Fragment nextFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_translate:
                    nextScreen = ActivityUtils.Screen.MAIN;
                    nextFragment = MainFragment.newInstance();
                    mMainPresenter = new MainPresenter(Injection.provideRepository(getApplicationContext()), (MainContract.View) nextFragment);
                    break;
                case R.id.navigation_note:
                    nextScreen = ActivityUtils.Screen.NOTE;
                    nextFragment = NoteFragment.newInstance();
                    break;
                case R.id.navigation_settings:
                    nextScreen = ActivityUtils.Screen.SETTINGS;
                    nextFragment = SettingsFragment.newInstance();
                    break;
            }
            if (nextScreen != null) {
                ActivityUtils.AnimationSide animationSide = ActivityUtils.getAnimationSize(currentScreen, nextScreen);
                ActivityUtils.addFragmentToActivity(
                        getSupportFragmentManager(), nextFragment, R.id.contentFrame, animationSide);
                currentScreen = nextScreen;
                return true;
            } else {
                return false;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MainFragment mainFragment =
                (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mainFragment == null) {
            currentScreen = ActivityUtils.Screen.MAIN;
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.contentFrame, ActivityUtils.AnimationSide.NONE);
        }

        mMainPresenter = new MainPresenter(Injection.provideRepository(getApplicationContext()), mainFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenter.onStart(getBaseContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.onStop(getBaseContext());
    }
}
