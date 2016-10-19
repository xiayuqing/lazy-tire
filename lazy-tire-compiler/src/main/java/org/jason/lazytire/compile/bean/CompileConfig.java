package org.jason.lazytire.compile.bean;

import java.io.File;
import java.util.Map;


/**
 * Created by Jason.Xia on 16/10/18.
 */
public class CompileConfig {
    private String projectName;
    private File project;
    private CompileType compileType;
    private Map<String, Object> extra;

    public File getProject() {
        return project;
    }

    public void setProject(File project) {
        this.project = project;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
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
}