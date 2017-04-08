package com.example.aleksei.yatranslator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.aleksei.yatranslator.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

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

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {

            currentScreen = ActivityUtils.Screen.MAIN;
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), MainFragment.newInstance(), R.id.contentFrame, ActivityUtils.AnimationSide.NONE);
        }
    }

}
