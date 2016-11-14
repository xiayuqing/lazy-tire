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
    private static final Properties CONF = new Properties();
    private static final String CONFIG_FILE_NAME = "lazytire-admin.cfg";
    private static AtomicBoolean load = new AtomicBoolean(false);

    public static final String GIT_ADMIN_ACCOUNT="gitlab.admin.account";
    public static final String GIT_ADMIN_PASSWD="gitlab.admin.passwd";
    public static final String GIT_ADMIN_PRIVATE_TOKEN="gitlab.admin.privatetoken";
    public static final String GIT_ADMIN_ADDRESS="gitlab.address";
    public static final String MAVEN_HOME="maven.home";
    public static final String REPOSITORY_PROJECT="repository.project";
    public static final String REPOSITORY_REPORT="repository.report";

    private AppContext() {
    }

    public static void load(String confPath) {
        String path = null;
        try {
            if (load.compareAndSet(false, true)) {
                if (null == confPath || 0 == confPath.length()) {
                    path = CONFIG_FILE_NAME;
                    InputStream is = AppContext.class.getClassLoader().getResourceAsStream(path);
                    CONF.load(is);
                } else {
                    path = confPath + CONFIG_FILE_NAME;
                    InputStream is = new FileInputStream(new File(path));
                    CONF.load(is);
                }

            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load config : " + path, e);
        }
    }

    public static String getProperty(String name) {
        return CONF.get(name).toString();
    }

    public static String getProperty(String name, String defaultV) {
        String result = CONF.get(name).toString();
        if (null == result || "".equals(result)) {
            return defaultV;
        }

        return result;
    }
}
