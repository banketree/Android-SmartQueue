package com.sunzx.smart;

import android.app.Activity;
import android.view.View;


/**
 * Created by 晓勇 on 2015/9/11 0011.
 */
public class ViewPriorityQueue<E extends View> {
    private final ThreadPriorityQueue<Thread> mQueue;
    private ViewPriorityQueue(ViewBuilder builder){
        this.mQueue = builder.mQueue;
    }
    public void cancelAll(){
        if (mQueue!=null){
            mQueue.removeAll();
        }
    }
    public void run(){
        if(mQueue!=null){
            mQueue.run();
        }
    }

    public static  class ViewBuilder {
        private final ThreadPriorityQueue<Thread> mQueue;
        private Activity activity;
        private int index = 0;
        private ViewAnim anim;
        public ViewBuilder(Activity activity,ViewAnim anim) {
            this.activity = activity;
            mQueue = new ThreadPriorityQueue.QueueBuilder().create();
            this.anim = anim;
        }
        public ViewBuilder addView(View view) {
            if (view != null) {
                if(anim!=null){
                    anim.beforeViewAnim(view);
                }else{
                    view.setAlpha(0);
                }
                mQueue.addThread(showViewThread(activity,view,anim), index < 10 ? 10 - index : 1);
                index++;
            }
            return this;
        }

        public ViewPriorityQueue create() {
            return new ViewPriorityQueue(this);
        }

    }

    private static Thread showViewThread(final Activity activity, final View view,final ViewAnim anim){
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            if(anim!=null){
                                anim.runViewAnim(view);
                            }else{
                                view.animate().alpha(1f).setDuration(600).scaleY(1).scaleX(1).start();
                            }
                        }
                    }
                });
            }
        });
        return thread;
    }



}
