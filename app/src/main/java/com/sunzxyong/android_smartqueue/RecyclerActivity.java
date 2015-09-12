package com.sunzxyong.android_smartqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.sunzx.smart.ViewAnim;
import com.sunzx.smart.ViewPriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ViewPriorityQueue viewPriorityQueue;
    private int itemViewNum;//屏幕能显示多少个item
    private boolean firstIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(getDatas());
        mRecyclerView.setAdapter(adapter);
        final List<View> views = new ArrayList<>();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int first = linearLayoutManager.findFirstVisibleItemPosition();
                int last = linearLayoutManager.findLastVisibleItemPosition();
                itemViewNum = last - first + 1;

                if (!firstIn && last == 7) {
                    firstIn = true;
                    for (int i = 0; i < itemViewNum; i++) {
                        views.add(mRecyclerView.findViewWithTag(i));
                    }
                    viewPriorityQueue = new ViewPriorityQueue.ViewBuilder(new MainActivity(), new ViewAnim() {
                        @Override
                        public void beforeViewAnim(View view) {
//                            animBefore1(view);
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            view.setAlpha(0);
                            view.animate().translationY(metrics.heightPixels).start();
                        }

                        @Override
                        public void runViewAnim(View view) {
//                            animIn1(view);
                            view.animate().alpha(1).translationY(0).setDuration(500).start();
                        }
                    }).addView(views.get(0)).addView(views.get(7)).addView(views.get(1)).addView(views.get(6)).addView(views.get(2)).addView(views.get(3)).addView(views.get(5)).addView(views.get(4)).create();
                    //View的执行顺序是按addView()的顺序，越前表示越先显示
                    viewPriorityQueue.run();
                }
            }
        });

    }
    private void animBefore1(View view){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        if ((int) (view.getTag()) % 2 == 0) {
            view.setAlpha(0);
            view.animate().translationX(-(metrics.widthPixels - view.getLeft())).start();
        } else {
            view.setAlpha(0);
            view.animate().translationX(metrics.widthPixels - view.getLeft()).start();
        }
    }
    private void animIn1(View view){
        view.setAlpha(1);
        view.animate().translationX(0).setDuration(500).start();
    }
    public List<String> getDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (viewPriorityQueue != null) {
            viewPriorityQueue.cancelAll();
        }
    }
}
