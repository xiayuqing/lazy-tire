package org.jason.lazytire.compile;

import org.jason.lazytire.compile.bean.CompileConfig;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class CompilerManagerFactory {
    private static final CompilerManager manager = new CompilerManager();

    private static final AtomicBoolean started = new AtomicBoolean(false);

    public static void start(String confPath) {
        if (!started.compareAndSet(false, true)) {
            return;
        }

        CompileConfig cfg = CompilerContextLoader.load(confPath);
    }
}
