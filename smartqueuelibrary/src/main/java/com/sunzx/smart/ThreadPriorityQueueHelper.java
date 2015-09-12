package com.sunzx.smart;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by 晓勇 on 2015/9/10 0010.
 */
public class ThreadPriorityQueueHelper implements Comparator<Thread>,Serializable {
    @Override
    public int compare(Thread lhs, Thread rhs) {
        int value = lhs.getPriority() < rhs.getPriority() ? 1 : lhs.getPriority() > rhs.getPriority() ? -1 : 0;
        return value;
    }
}
