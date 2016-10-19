package org.jason.lazytire.vcs.support;

import org.jason.lazy.tire.common.AppContext;
import org.jason.lazytire.vcs.VersionController;

import java.io.File;

/**
 * Created by Jason.Xia on 16/10/19.
 */
public abstract class AbstractVersionController<T> implements VersionController {

    protected AppContext context;

    public AbstractVersionController(AppContext context) {
        this.context = context;
    }

    protected abstract T openRepository(String projectName);

    protected boolean repositoryExists(String projectName) {
        return new File(context.getREPORT_REPOSITORY() + "/" + projectName).exists();
    }
}
