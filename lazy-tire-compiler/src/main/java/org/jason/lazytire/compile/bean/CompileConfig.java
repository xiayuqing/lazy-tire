package org.jason.lazytire.compile.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class CompileConfig {

    private String mavenHome;
    private String projectRepository;
    private String reportRepository;
    private Map<String, Integer> poolConfig;
    private int poolCore;
    private int poolMax;
    // second
    private int poolAlive;
    private int poolQueue;

    public String getMavenHome() {
        return mavenHome;
    }

    public void setMavenHome(String mavenHome) {
        this.mavenHome = mavenHome;
    }

    public String getProjectRepository() {
        return projectRepository;
    }

    public void setProjectRepository(String projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String getReportRepository() {
        return reportRepository;
    }

    public void setReportRepository(String reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Map<String, Integer> getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(Map<String, Integer> poolConfig) {
        this.poolConfig = poolConfig;
    }
}