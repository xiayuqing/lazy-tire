package org.jason.lazy.tire.common.bean;

import java.util.List;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class DeployConf {
    private String id;
    private String namespace;
    private String project;
    private String branch;
    private String env;
    private String path;
    private String host;
    private String sysAccount;
    private String sysPw;
    private boolean skipTest;
    private List<String> goals;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    public String getSysPw() {
        return sysPw;
    }

    public void setSysPw(String sysPw) {
        this.sysPw = sysPw;
    }

    public boolean isSkipTest() {
        return skipTest;
    }

    public void setSkipTest(boolean skipTest) {
        this.skipTest = skipTest;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }
}
