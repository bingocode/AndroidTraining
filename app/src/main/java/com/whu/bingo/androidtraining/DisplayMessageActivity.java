package com.whu.bingo.androidtraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView textView;
    Intent intent;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        initData();
        initView();
    }
    private void initData(){
        intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

}
