package org.jason.lazytire.compile;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class CompilerAgent {
    private ThreadPoolExecutor executor;
    private String MAVEN_HOME;
    private String PROJECT_REPOSITORY;
    private String REPORT_REPOSITORY;
    private long shutdownTimeout = 0;
    private int executorCoreSize = 0;
    private int executorMaxSize = 0;
    private int executorAliveSecond = 0;
    private int executorQueueSize = 0;
    private AtomicBoolean enable = new AtomicBoolean(true);
    private AtomicBoolean started = new AtomicBoolean(false);

    public void start() {

        if (!started.compareAndSet(false, true)) {
            return;
        }

        executor = new ThreadPoolExecutor(executorCoreSize, executorMaxSize, executorAliveSecond, TimeUnit.SECONDS,
                new LinkedBlockingDeque(executorQueueSize));

    }

    public void stop() {
        enable.set(false);
        if (0 == shutdownTimeout) {
            executor.shutdownNow();
        } else {
            executor.shutdown();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!executor.isShutdown()) {
                        executor.shutdownNow();
                    }
                }
            }, shutdownTimeout);
        }
    }

    public String getMAVEN_HOME() {
        return MAVEN_HOME;
    }

    public void setMAVEN_HOME(String MAVEN_HOME) {
        this.MAVEN_HOME = MAVEN_HOME;
    }

    public String getPROJECT_REPOSITORY() {
        return PROJECT_REPOSITORY;
    }

    public void setPROJECT_REPOSITORY(String PROJECT_REPOSITORY) {
        this.PROJECT_REPOSITORY = PROJECT_REPOSITORY;
    }

    public String getREPORT_REPOSITORY() {
        return REPORT_REPOSITORY;
    }

    public void setREPORT_REPOSITORY(String REPORT_REPOSITORY) {
        this.REPORT_REPOSITORY = REPORT_REPOSITORY;
    }

    public int getExecutorCoreSize() {
        return executorCoreSize;
    }

    public void setExecutorCoreSize(int executorCoreSize) {
        this.executorCoreSize = executorCoreSize;
    }

    public int getExecutorMaxSize() {
        return executorMaxSize;
    }

    public void setExecutorMaxSize(int executorMaxSize) {
        this.executorMaxSize = executorMaxSize;
    }

    public int getExecutorAliveSecond() {
        return executorAliveSecond;
    }

    public void setExecutorAliveSecond(int executorAliveSecond) {
        this.executorAliveSecond = executorAliveSecond;
    }

    public int getExecutorQueueSize() {
        return executorQueueSize;
    }

    public void setExecutorQueueSize(int executorQueueSize) {
        this.executorQueueSize = executorQueueSize;
    }

    public void setShutdownTimeout(long shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }
}
