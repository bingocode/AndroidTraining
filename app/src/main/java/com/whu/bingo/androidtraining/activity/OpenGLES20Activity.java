package com.whu.bingo.androidtraining.activity;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.whu.bingo.androidtraining.view.MyGLSurfaceView;

/**
 * Created by admin on 2017/11/7.
 */
public class OpenGLES20Activity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }
}
