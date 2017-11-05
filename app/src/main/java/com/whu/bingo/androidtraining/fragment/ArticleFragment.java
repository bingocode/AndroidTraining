package com.whu.bingo.androidtraining.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whu.bingo.androidtraining.R;


public class ArticleFragment extends Fragment {
    public final static String ARG_POSITION = "argposition";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }


    public void updateArticleView(int position){
        //处理与Activity的交互
        Toast.makeText(getActivity(),"点击后更新了文章",Toast.LENGTH_SHORT);
    }

}
