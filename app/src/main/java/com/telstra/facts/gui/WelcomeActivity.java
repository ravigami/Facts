package com.telstra.facts.gui;

/**
 * Created by ravi.gami on 3/8/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.telstra.facts.R;
import com.telstra.facts.commons.Constants;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends BaseActivity {
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        //Schedule handler to display splash screen for kSplashDisplayTimeout milliseconds.
        handler.postDelayed(new SplashScreenRunnable(this), Constants.kSplashDisplayTimeout);
    }

    /**
     *    * onDestroy free used resources for the activity.
     *    
     */
    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }

    /**
     * SplashScreenRunnable to avoid memory leak with WeakReference.
     */
    private static class SplashScreenRunnable implements Runnable {
        private final WeakReference activity;

        private SplashScreenRunnable(Activity act) {
            activity = new WeakReference<>(act);
        }

        @Override
        public void run() {
            if (activity.get() != null) {
                Activity act = (Activity) activity.get();
                Intent mainIntent = new Intent(act, FactsListActivity.class);
                act.startActivity(mainIntent);
                act.finish();
            }
        }
    }
}
