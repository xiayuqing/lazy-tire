package org.jason.lazytire.vcs.support;

import org.jason.lazy.tire.common.AppContext;
import org.jason.lazytire.vcs.VersionController;

import java.io.File;

/**
 * Created by Jason.Xia on 16/10/19.
 */
public abstract class AbstractVersionController<T> implements VersionController {


    public AbstractVersionController() {

    }

    protected abstract T openRepository(String projectName);

    protected boolean repositoryExists(String projectName) {
        return new File(AppContext.getProperty(AppContext.REPOSITORY_PROJECT) + "/" + projectName).exists();
    }
}
