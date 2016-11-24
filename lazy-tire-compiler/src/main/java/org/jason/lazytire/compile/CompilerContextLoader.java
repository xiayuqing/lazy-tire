package org.jason.lazytire.compile;

import org.jason.lazy.tire.common.StringConstant;
import org.jason.lazy.tire.common.utils.Assert;
import org.jason.lazytire.compile.bean.CompileConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class CompilerContextLoader {

    public static CompileConfig load(String confPath) {
        String path = confPath + "/lazytire-compiler.cfg";
        Properties properties = new Properties();
        InputStream is;
        try {
            is = new FileInputStream(path);

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Can not find " + path);
        }

        try {
            properties.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Can not resolved compile config file :" + path, e);
        }

        CompileConfig config = new CompileConfig();
        try {
            String mavenHome = properties.getProperty(StringConstant.MAVEN_HOME);
            Assert.hasText(mavenHome, "maven home not be null.");
            config.setMavenHome(mavenHome);

            String projectRepos = properties.getProperty(StringConstant.REPOSITORY_PROJECT);
            Assert.hasText(projectRepos, "project repository not be null.");
            config.setProjectRepository(projectRepos);

            String reportRepos = properties.getProperty(StringConstant.REPOSITORY_REPORT);
            Assert.hasText(reportRepos, "report repository not be null.");
            config.setReportRepository(reportRepos);

            Map<String, Integer> poolConfigs = new HashMap<>();
            Map<String, String> extConfig = new HashMap<>();
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String key = entry.getKey().toString();
                if (key.startsWith("pool.")) {
                    Integer value = null == entry.getValue() ? -1 : (Integer) entry.getValue();
                    poolConfigs.put(key.replace("pool.", ""), value);
                } else if (key.startsWith("config.")) {
                    extConfig.put(key.replace("config.", ""), entry.getValue().toString());
                }
            }

            config.setPoolConfig(poolConfigs);
            config.setConfig(extConfig);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return config;
    }
}
