package org.jason.lazytire.compile.bean;

import java.io.File;
import java.util.Map;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class CompileConfig {

    private String mavenHome;
    private String projectRepository;
    private String reportRepository;
    private Map<String, Integer> poolConfig;
    private Map<String, String> config;
    private File project;
    private CompileType compileType;
    private String projectName;

    public File getProject() {
        return project;
    }

    public void setProject(File project) {
        this.project = project;
    }

    public CompileType getCompileType() {
        return compileType;
    }

    public void setCompileType(CompileType compileType) {
        this.compileType = compileType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

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

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

}