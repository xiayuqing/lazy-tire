package org.jason.lazy.tire.common;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class ContextConfiguration {
    private String mavenHome;
    private String projectRepository;
    private String reportRepository;

    public ContextConfiguration(String mavenHome, String projectRepository, String reportRepository) {
        this.mavenHome = mavenHome;
        this.projectRepository = projectRepository;
        this.reportRepository = reportRepository;
    }

    public ContextConfiguration() {
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
}
