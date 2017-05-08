package org.jason.lazytire.compile;

import org.jason.lazy.tire.common.AppContext;
import org.jason.lazy.tire.common.Callback;
import org.jason.lazy.tire.common.bean.Result;
import org.jason.lazytire.compile.support.maven.MavenCompileTask;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Jason.Xia on 17/5/8.
 */
public class CompilePool {
    private ThreadPoolExecutor executor;
    private long shutdownTimeout = 0;
    private AtomicBoolean enable = new AtomicBoolean(true);
    private AtomicBoolean started = new AtomicBoolean(false);

    public static void main(String[] args) {
        AtomicBoolean s = new AtomicBoolean(false);
        s.compareAndSet(true, false);
        System.out.println(s);
    }

    @SuppressWarnings("unchecked")
    public void start() {
        if (!started.compareAndSet(false, true)) {
            return;
        }

        shutdownTimeout = AppContext.getInt(AppContext.Field.COMPILE_SHUTDOWN_TIMEOUT, 0);
        int coreSize = AppContext.getInt(AppContext.Field.COMPILE_POOL_CORE, 5);
        int maxSize = AppContext.getInt(AppContext.Field.COMPILE_POOL_MAX, 10);
        int aliveMilli = AppContext.getInt(AppContext.Field.COMPILE_POOL_ALIVE, 10);
        int queueSize = AppContext.getInt(AppContext.Field.COMPILE_POOL_QUEUE, 20);

        executor = new ThreadPoolExecutor(coreSize, maxSize, aliveMilli, TimeUnit.MILLISECONDS, new
                LinkedBlockingQueue(queueSize));
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

    public Future<Result> addTask(Option option) {
        if (!enable.get()) {
            throw new UnableException();
        }

        Future<Result> submit = executor.submit(new MavenCompileTask(option, new Callback() {
            @Override
            public void call() {
                CompilePool.this.enable.compareAndSet(false, true);
            }
        }));

        synchronized (executor) {
            if (0 >= executor.getQueue().remainingCapacity()) {
                enable.set(false);
            }
        }

        return submit;
    }

    public boolean cancelTask(Future<Result> future) {
        if (future.isDone()) {
            throw new IllegalStateException("task is already done, can't cancel");
        }

        if (future.isCancelled()) {
            throw new IllegalStateException("task is already cancelled");
        }

        return future.cancel(true);
    }
}
