package com.telstra.facts.gui;

/**
 * Created by ravi.gami on 3/8/17.
 */


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Generic implementation for the activity goes here. Rather than duplicate code, single reusable
 * code can be written in BaseActivity.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity{
    private static final String TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
    }
}
