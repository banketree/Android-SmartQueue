package com.sunzx.smart;

import android.os.AsyncTask;

import java.util.PriorityQueue;

/**
 * Created by 晓勇 on 2015/9/10 0010.
 */
public class ThreadPriorityQueue<E extends Thread> {
    private final PriorityQueue<E> mQueue;
    private boolean queueIsRunning = false;
    private ThreadAsyncTask mTask;
    private ThreadPriorityQueue(QueueBuilder builder) {
        mQueue = builder.mQueue;
    }

    /**
     * Cancel the execution of all threads
     */
    public void removeAll() {
        if (mQueue!=null&&!mQueue.isEmpty()) {
            mQueue.clear();
        }
        if(mTask!=null){
            mTask.cancel(true);
        }
    }
    public void addThread(E t,int priority){
        if (t != null) {
            int pId = (priority>=0 && priority<=10) ? priority : 1;
            t.setPriority(pId);
            mQueue.add(t);
        }
    }
    /**
     * @param t remove a Thread
     * @return
     */
    public boolean removeThread(Thread t) {
        return t != null && mQueue.remove(t);
    }
    public int size(){
        return mQueue!=null?mQueue.size():0;
    }
    private void runByOrder(){
        if (threadQueueIsRunning()) {
            return;
        }
        queueIsRunning = true;
        Thread mThread = null;
        boolean flag = false;
        int i = 0;
        int size = mQueue.size();
        while (!mQueue.isEmpty()) {
            if (!flag) {
                mThread = mQueue.poll();
                mThread.start();
                flag = true;
                i++;
            }
            if (mThread.getState() == Thread.State.TERMINATED && !mQueue.isEmpty()) {
                if (size == i) {
                    break;
                }
                if (size == (i + 1)) {
                    mThread = mQueue.peek();
                    mThread.start();
                    i++;
                } else {
                    mThread = mQueue.poll();
                    mThread.start();

                    i++;
                }
            }
        }
        queueIsRunning = false;
    }

    /**
     * execute Thread by order
     */
    public void run() {
        mTask = new ThreadAsyncTask(this);
        mTask.execute();
    }
    class ThreadAsyncTask extends AsyncTask<Void,Void,Void> {
        ThreadPriorityQueue threadQueue;
        public ThreadAsyncTask(ThreadPriorityQueue queue){
            this.threadQueue = queue;
        }
        @Override
        protected Void doInBackground(Void... params) {
            threadQueue.runByOrder();
            return null;
        }
    }
    private boolean threadQueueIsRunning() {
        return queueIsRunning;
    }
    public static class QueueBuilder{
        private final PriorityQueue mQueue;
        public QueueBuilder(){
            mQueue = new PriorityQueue<>(11,new ThreadPriorityQueueHelper());
        }
        public QueueBuilder addThread(Thread t,int priority){
            if (t != null) {
                int pId = (priority>=0 && priority<=10) ? priority : 1;
                t.setPriority(pId);
                mQueue.add(t);
            }
            return this;
        }
        public ThreadPriorityQueue create(){
            return new ThreadPriorityQueue(this);
        }
    }

}
