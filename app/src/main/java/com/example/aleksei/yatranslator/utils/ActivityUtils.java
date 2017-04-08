package com.example.aleksei.yatranslator.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.example.aleksei.yatranslator.R;

public class ActivityUtils {

    public enum Screen {
        MAIN(0), NOTE(1), SETTINGS(2);

        Screen(int order) {
            this.order = order;
        }

        private int order;

        public int getOrder() {
            return order;
        }
    }

    public static AnimationSide getAnimationSize(Screen currentScreen, Screen nextScreen) {
        if (currentScreen == nextScreen) {
            return AnimationSide.NONE;
        }
        return currentScreen.getOrder() > nextScreen.getOrder() ? AnimationSide.LEFT : AnimationSide.RIGHT;
    }

    public enum AnimationSide {
        NONE, LEFT, RIGHT
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId, AnimationSide animationSide) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (animationSide) {
            case RIGHT:
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case LEFT:
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case NONE:
                break;
        }
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

}
