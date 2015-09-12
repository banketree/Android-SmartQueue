package com.sunzxyong.android_smartqueue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sunzx.smart.ThreadPriorityQueue;
import com.sunzx.smart.ViewPriorityQueue;

public class MainActivity extends AppCompatActivity {
    private Thread thread1, thread2, thread3, thread4, thread5, thread6;
    private ThreadPriorityQueue mThreadPriorityQueue;
    private ViewPriorityQueue viewPriorityQueue;
    private TextView mTextView;
    private Button mButton,mButton2,mButton3,mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_thread);
        mButton = (Button) findViewById(R.id.btn_run);
        mButton2 = (Button) findViewById(R.id.btn_run2);
        mButton3 = (Button) findViewById(R.id.btn_run3);
        mButton4 = (Button) findViewById(R.id.btn_change);

        createThread();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createThread();
                mTextView.setText("开始顺序执行线程");
                mThreadPriorityQueue = new ThreadPriorityQueue.QueueBuilder()
                        .addThread(thread1, 10).addThread(thread2, 9)
                        .addThread(thread3, 8).addThread(thread4, 7)
                        .addThread(thread5, 6).addThread(thread6, 5)
                        .create();
                mThreadPriorityQueue.run();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createThread();
                mTextView.setText("开始逆序执行线程");
                mThreadPriorityQueue = new ThreadPriorityQueue.QueueBuilder()
                        .addThread(thread1, 1).addThread(thread2, 2)
                        .addThread(thread3, 3).addThread(thread4, 4)
                        .addThread(thread5, 5).addThread(thread6,6)
                        .create();
                mThreadPriorityQueue.run();
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createThread();
                mTextView.setText("开始按135246顺序执行线程");
                //线程的执行顺序，在添加Thread的第二个参数表示优先级，值越大表示越先执行，值范围只能在1~10中，超过将默认为1
                mThreadPriorityQueue = new ThreadPriorityQueue.QueueBuilder()
                        .addThread(thread1, 10).addThread(thread2, 7)
                        .addThread(thread3, 9).addThread(thread4, 6)
                        .addThread(thread5, 8).addThread(thread6,5)
                        .create();
                mThreadPriorityQueue.run();
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mThreadPriorityQueue!=null){
            mThreadPriorityQueue.removeAll();//Activity停止时停止后续Thread任务
        }
    }

    public void createThread() {
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeUI("这是线程1，执行了2s");
            }
        });
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeUI("这是线程2，执行了1.5s");
            }
        });
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeUI("这是线程3，执行了1s");
            }
        });
        thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeUI("这是线程4，执行了0.8s");
            }
        });
        thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeUI("这是线程5，执行了1s");
            }
        });
        thread6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeUI("这是线程6，执行了0.5s");
            }
        });
    }

    private void changeUI(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(content);
            }
        });
    }
}
