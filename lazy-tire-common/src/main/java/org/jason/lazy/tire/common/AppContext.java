package org.jason.lazy.tire.common;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class AppContext {
    private String MAVEN_HOME;
    private String PROJECT_REPOSITORY;
    private String REPORT_REPOSITORY;
    private String GIT_ADMIN_ACCOUNT;
    private String GIT_ADMIN_PASSWORD;
    private String GIT_PRIVATE_TOKEN;
    private String GIT_ADDRESS;

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

    public String getGIT_ADMIN_ACCOUNT() {
        return GIT_ADMIN_ACCOUNT;
    }

    public void setGIT_ADMIN_ACCOUNT(String GIT_ADMIN_ACCOUNT) {
        this.GIT_ADMIN_ACCOUNT = GIT_ADMIN_ACCOUNT;
    }

    public String getGIT_ADMIN_PASSWORD() {
        return GIT_ADMIN_PASSWORD;
    }

    public void setGIT_ADMIN_PASSWORD(String GIT_ADMIN_PASSWORD) {
        this.GIT_ADMIN_PASSWORD = GIT_ADMIN_PASSWORD;
    }

    public String getGIT_PRIVATE_TOKEN() {
        return GIT_PRIVATE_TOKEN;
    }

    public void setGIT_PRIVATE_TOKEN(String GIT_PRIVATE_TOKEN) {
        this.GIT_PRIVATE_TOKEN = GIT_PRIVATE_TOKEN;
    }

    public String getGIT_ADDRESS() {
        return GIT_ADDRESS;
    }

    public void setGIT_ADDRESS(String GIT_ADDRESS) {
        this.GIT_ADDRESS = GIT_ADDRESS;
    }
}
