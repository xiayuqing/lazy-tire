package org.jason.lazy.tire.common.bean;

import java.io.Serializable;

/**
 * Created by Jason.Xia on 17/5/8.
 */
public class TaskStatistics implements Serializable {
    private long start;
    private long end;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
