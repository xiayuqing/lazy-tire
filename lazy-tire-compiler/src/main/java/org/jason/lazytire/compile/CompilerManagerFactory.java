package org.jason.lazytire.compile;

import org.jason.lazytire.compile.bean.CompileConfig;

import java.util.Map;
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

        try {
            CompileConfig cfg = CompilerContextLoader.load(confPath);
            manager.setMAVEN_HOME(cfg.getMavenHome());
            manager.setPROJECT_REPOSITORY(cfg.getProjectRepository());
            manager.setREPORT_REPOSITORY(cfg.getReportRepository());
            if (0 != cfg.getPoolConfig().size()) {
                Map<String, Integer> config = cfg.getPoolConfig();
                manager.setExecutorCoreSize(null != config.get("core") ? 5 : config.get("core"));
                manager.setExecutorMaxSize(null != config.get("max") ? 10 : config.get("max"));
                manager.setExecutorMaxSize(null != config.get("alive") ? 10 : config.get("alive"));
                manager.setExecutorMaxSize(null != config.get("queue") ? 20 : config.get("queue"));
            }

            manager.start();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    manager.stop();
                }
            }));
        } catch (Exception e) {
            System.err.println("CompilerManager Startup Error: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
