package org.jason.lazy.tire.common;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class AppContext {
    private String MAVEN_HOME;
    private String PROJECT_REPOSITORY;
    private String REPORT_REPOSITORY;

    public AppContext(ContextConfiguration config) {
        this.MAVEN_HOME = config.getMavenHome();
        this.PROJECT_REPOSITORY = config.getProjectRepository();
        this.REPORT_REPOSITORY = config.getReportRepository();
    }

    public String getMAVEN_HOME() {
        return MAVEN_HOME;
    }

    public void setMAVEN_HOME(String MAVEN_HOME) {
        this.MAVEN_HOME = MAVEN_HOME;
    }

    public String getPROJECT_REPOSITORY() {
        return PROJECT_REPOSITORY;
    }

    public void setPROJECT_REPOSITORY(String PROJECT_REPOSITORY) {
        this.PROJECT_REPOSITORY = PROJECT_REPOSITORY;
    }

    public String getREPORT_REPOSITORY() {
        return REPORT_REPOSITORY;
    }

    public void setREPORT_REPOSITORY(String REPORT_REPOSITORY) {
        this.REPORT_REPOSITORY = REPORT_REPOSITORY;
    }
}
