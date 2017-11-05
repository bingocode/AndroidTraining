package com.whu.bingo.androidtraining.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.whu.bingo.androidtraining.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlinesFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_headlines, container, false);
    }

    OnHeadlineSelectedListener mCallback;

    // 容器 Activity 必须实现该接口
    // （译注：“容器 Activity”意即“包含该 Fragment 的 Activity”）
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 确认容器 Activity 已实现该回调接口。否则，抛出异常
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // 向宿主 Activity 传送事件
        mCallback.onArticleSelected(position);
    }

}
