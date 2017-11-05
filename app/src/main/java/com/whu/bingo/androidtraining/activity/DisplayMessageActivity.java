package com.whu.bingo.androidtraining.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.whu.bingo.androidtraining.R;
import com.whu.bingo.androidtraining.activity.MainActivity;

public class DisplayMessageActivity extends FragmentActivity {
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
        /*
        获取SharePreference对象的两种方式:一般是这种：
         */
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
        /*
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);//获取 Activity 的一个共享首选项文件
         */
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    //分享时显示应用选择器
    public void sharetext(View view){
        Toast.makeText(this,"share",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        intent.setType("text/plain"); //需要加上这句，官网没有加上，所以最后会找不到。
        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = message;
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);

        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this,"sharesoming",Toast.LENGTH_SHORT).show();
            startActivity(chooser);
        }
    }


}
