package org.jason.lazytire.compile.support;

import org.jason.lazytire.compile.Compiler;
import org.jason.lazytire.compile.bean.CompileConfig;

import java.io.File;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class AbstractCompiler implements Compiler {
    protected File project;
    protected CompileConfig config;

    @Override
    public void init(CompileConfig config) {
        this.config = config;
        if (null == config.getProject() || !config.getProject().exists()) {
            throw new IllegalArgumentException("Project not be null in compile configuration.");
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public File getProject() {
        return project;
    }

    public void setProject(File project) {
        this.project = project;
    }

    public CompileConfig getConfig() {
        return config;
    }
}
