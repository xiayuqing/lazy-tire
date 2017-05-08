package org.jason.lazy.tire.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class AppContext {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_NAME = "lazytire-admin.cfg";
    private static AtomicBoolean load = new AtomicBoolean(false);

    public AppContext() {
    }

    public static void load(String confPath) {
        String path = null;
        try {
            if (load.compareAndSet(false, true)) {
                if (null == confPath || 0 == confPath.length()) {
                    path = CONFIG_FILE_NAME;
                    InputStream is = AppContext.class.getClassLoader().getResourceAsStream(path);
                    properties.load(is);
                } else {
                    path = confPath + CONFIG_FILE_NAME;
                    InputStream is = new FileInputStream(new File(path));
                    properties.load(is);
                }

            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load config : " + path, e);
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        String p = properties.getProperty(key);
        if (null == p || 0 == p.length()) {
            return def;
        }

        return Boolean.valueOf(p);
    }

    public static int getInt(String key, int def) {
        String p = properties.getProperty(key);
        if (null == p || 0 == p.length()) {
            return def;
        }

        return Integer.valueOf(p);
    }

    public static long getLong(String key, long def) {
        String p = properties.getProperty(key);
        if (null == p || 0 == p.length()) {
            return def;
        }

        return Long.valueOf(p);
    }

    public static String getString(String key, String def) {
        String p = properties.getProperty(key);
        if (null == p || 0 == p.length()) {
            return def;
        }

        return p;
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public interface Field {
        String GIT_ADMIN_ACCOUNT = "gitlab.admin.account";
        String GIT_ADMIN_PASSWD = "gitlab.admin.passwd";
        String GIT_ADMIN_PRIVATE_TOKEN = "gitlab.admin.privatetoken";
        String GIT_ADMIN_ADDRESS = "gitlab.address";
        String MAVEN_HOME = "maven.home";
        String REPOSITORY_PROJECT = "repository.project";
        String REPOSITORY_REPORT = "repository.report";

        String COMPILE_SHUTDOWN_TIMEOUT="compile.shutdown.timeout";
        String COMPILE_POOL_CORE="compile.pool.core";
        String COMPILE_POOL_MAX="compile.pool.max";
        String COMPILE_POOL_ALIVE="compile.pool.alive";
        String COMPILE_POOL_QUEUE="compile.pool.queue";
    }
}
