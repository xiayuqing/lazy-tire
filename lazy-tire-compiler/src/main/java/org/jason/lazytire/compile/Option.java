package org.jason.lazytire.compile;

import org.jason.lazytire.compile.bean.CompileType;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Created by Jason.Xia on 17/5/8.
 */
public class Option implements Serializable {
    private String projectName;
    private List<String> goals;
    private List<String> profiles;
    private Properties properties;
    private CompileType compileType;

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

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
