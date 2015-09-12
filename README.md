# Android-SmartQueue
基于优先级队列写的一个SmartQueue（可控制多个线程的顺序执行、View的顺序显示）

----

#效果：
![](http://a2.qpic.cn/psb?/V11ObwOq0j7CZA/x30V50L7hwVu6BkA1ySP96m8qaD6ellVjkvbvJzckA4!/b/dIoBAAAAAAAA&bo=mAGRAgAAAAACMx0!&rf=viewer_4)![](http://a3.qpic.cn/psb?/V11ObwOq0j7CZA/eCFpWdnQd8LzgLVNLz1U0ZyqjY7XxopTGQC5nXdn8zg!/c/dIgBAAAAAAAA&bo=mAGRAgAAAAACNBo!&rf=viewer_4)

![](http://a2.qpic.cn/psb?/V11ObwOq0j7CZA/ZPHVE4WZ4WStp7i.aKCf.XKofO7IySVsCyqqElXM3Rk!/c/dIoBAAAAAAAA&bo=lgGMAgAAAAACNAk!&rf=viewer_4)![](http://a3.qpic.cn/psb?/V11ObwOq0j7CZA/ec*qL0KpOAMKUHdj1QHO03yZejOyHqF0w4x1lEtEXws!/c/dG4AAAAAAAAA&bo=lQGMAgAAAAACHyE!&rf=viewer_4)

![](http://a3.qpic.cn/psb?/V11ObwOq0j7CZA/J3JnWarSxjTixcbr9UsiICxn*sK4GfspTMutP1SBdSg!/c/dG4AAAAAAAAA&bo=mQGRAgAAAAACT2A!&rf=viewer_4)

#Usage
##多个线程顺序执行
你可以创建一个`ThreadPriorityQueue`对象，然后通过.run()方法让线程开始执行，创建`ThreadPriorityQueue`对象的时候，你可以通过`addThread()`方法添加线程，其中第一个参数是Thread对象，第二个参数是你自己设置线程的优先级（值范围是1~10，优先级越高线程越先执行，当设置的值不在这个范围则默认为1）：
```java
ThreadPriorityQueue mThreadPriorityQueue = new ThreadPriorityQueue.QueueBuilder()
                        .addThread(thread1, 10).addThread(thread2, 9)
                        .addThread(thread3, 8).addThread(thread4, 7)
                        .addThread(thread5, 6).addThread(thread6, 5)
                        .create();
mThreadPriorityQueue.run();
```
当你想停止线程的执行，你可以调用：
```java
if(mThreadPriorityQueue!=null){
      mThreadPriorityQueue.removeAll();//停止后续Thread任务
  }
```
##多个View的顺序显示
你可以添加任何的View，来控制它们的显示顺序，正如上面的效果一样，你可以控制Activity中布局的显示顺序，也可以控制列表项的显示，通过控制View的显示顺序你让界面不同的动画效果，如：
```java
ViewPriorityQueue viewPriorityQueue = new ViewPriorityQueue.ViewBuilder(this,null).addView(views.get(3)).addView(views.get(5)).addView(views.get(4)).create();
        //View的执行顺序是按addView()的顺序，越前表示越先显示
viewPriorityQueue.run();
```
其中和第一种方式一样先创建一个`ViewPriorityQueue`对象，其中`ViewBuilder()`中有两个参数，第一个是当前的Activity对象，第二个是实现一个ViewAnim接口（为null表示View的显示是默认的效果，或者自己实现这个接口自定义View的显示动画效果），这个`addView()`方法表示添加View，越先添加的View越先显示，所以你想让某个View最先显示你可以把它第一个添加。
如下就是我自定义的一种动画效果：
```java
ViewPriorityQueue viewPriorityQueue = new ViewPriorityQueue.ViewBuilder(new MainActivity(), new ViewAnim() {
                        @Override
                        public void beforeViewAnim(View view) {
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            view.setAlpha(0);
                            view.animate().translationY(metrics.heightPixels).start();
                        }

                        @Override
                        public void runViewAnim(View view) {
                            view.animate().alpha(1).translationY(0).setDuration(500).start();
                        }
                    }).addView(views.get(0)).addView(views.get(7)).addView(views.get(1)).addView(views.get(6)).addView(views.get(2)).addView(views.get(3)).addView(views.get(5)).addView(views.get(4)).create();

                    viewPriorityQueue.run();
```
当然你也可以定义一个layout中的不同控件的显示顺序。
