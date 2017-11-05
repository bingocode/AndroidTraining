package com.whu.bingo.androidtraining.activity;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whu.bingo.androidtraining.R;
import com.whu.bingo.androidtraining.fragment.ArticleFragment;
import com.whu.bingo.androidtraining.fragment.HeadlinesFragment;


public class FragmentTestActivity extends AppCompatActivity implements HeadlinesFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);
        initfragment(savedInstanceState);

    }

    /**
     * 添加fragment
     * @param savedInstanceState
     */
    public void initfragment(Bundle savedInstanceState){
        if (findViewById(R.id.fragment_container) != null) {//判断当前加载的layout是小屏幕下的news_articles.xml

            // 不过，如果我们要从先前的状态还原，则无需执行任何操作而应返回，否则
            // 就会得到重叠的 Fragment
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // 如果此 Activity 是通过 Intent 发出的特殊指令来启动的，
            // 请将该 Intent 的 extras 以参数形式传递给该 Fragment
            firstFragment.setArguments(getIntent().getExtras());

            // 将该 Fragment 添加到“fragment_container” FrameLayout 中
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    /**
     * 替换fragment
     */
    public void replacefragment(){
        // 创建 Fragment 并为其添加一个参数，用来指定应显示的文章
        ArticleFragment newFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_POSITION, 0);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 将 fragment_container View 中的内容替换为此 Fragment，
        // 然后将该事务添加到返回堆栈，以便用户可以向后导航
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // 执行事务
        transaction.commit();
    }

    /**
     * 实现Activity与Fragment事件交互（Fragment将事件传给Activity处理）
     * @param position
     */
    public void onArticleSelected(int position) {
        // 用户从 HeadlinesFragment 选择了一篇文章的标题
        // 在这里做点什么，以显示该文章

        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // 若 articleFrag 有效，则表示我们正在处理两格布局（two-pane layout）……

            // 调用 ArticleFragment 的方法，以更新其内容
            articleFrag.updateArticleView(position);
        } else {
            // 否则，我们正在处理单格布局（one-pane layout）。此时需要 swap frags...

            // 创建 Fragment，向其传递包含被选文章的参数
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // 无论 fragment_container 视图里是什么，用该 Fragment 替换它。并将
            // 该事务添加至回栈，以便用户可以往回导航（译注：回栈，即 Back Stack。
            // 在有多个 Activity 的 APP 中，将这些 Activity 按创建次序组织起来的
            // 栈，称为回栈）
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // 执行事务
            transaction.commit();
        }

    }




    }

