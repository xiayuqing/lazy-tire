package org.jason.lazytire.compile;

import org.jason.lazy.tire.common.utils.StringUtils;
import org.jason.lazytire.compile.bean.CompileConfig;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class CompilerAgentFactory {
    private static final CompilerAgent agent = new CompilerAgent();

    private static final AtomicBoolean started = new AtomicBoolean(false);

    public static void start(String confPath) {
        if (!started.compareAndSet(false, true)) {
            return;
        }

        try {
            CompileConfig cfg = CompilerContextLoader.load(confPath);
            agent.setMAVEN_HOME(cfg.getMavenHome());
            agent.setPROJECT_REPOSITORY(cfg.getProjectRepository());
            agent.setREPORT_REPOSITORY(cfg.getReportRepository());
            if (0 != cfg.getPoolConfig().size()) {
                Map<String, Integer> config = cfg.getPoolConfig();
                agent.setExecutorCoreSize(null != config.get("core") ? 5 : config.get("core"));
                agent.setExecutorMaxSize(null != config.get("max") ? 10 : config.get("max"));
                agent.setExecutorMaxSize(null != config.get("alive") ? 10 : config.get("alive"));
                agent.setExecutorMaxSize(null != config.get("queue") ? 20 : config.get("queue"));
            }

            if (0 != cfg.getConfig().size()) {
                Map<String, String> config = cfg.getConfig();
                if (config.containsKey("shutdown.timeout")) {
                    if (!StringUtils.isNum(config.get("shutdown.timeout"))) {
                        throw new IllegalArgumentException("config.shutdown.timeout value must be number.");
                    }

                    agent.setShutdownTimeout(Long.valueOf(config.get("shutdown.timeout")));
                }
            }

            agent.start();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    agent.stop();
                }
            }));
        } catch (Exception e) {
            System.err.println("CompilerManager Startup Error: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
