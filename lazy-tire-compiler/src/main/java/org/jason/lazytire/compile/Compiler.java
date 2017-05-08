package org.jason.lazytire.compile;

import org.jason.lazy.tire.common.bean.Result;
import org.jason.lazytire.compile.bean.CompileConfig;

import java.util.concurrent.Callable;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public interface Compiler extends Callable<Result> {
    void init(CompileConfig config);

    void run(Option option);

    void stop();

    void afterRun();

    void beforeRun();
}
