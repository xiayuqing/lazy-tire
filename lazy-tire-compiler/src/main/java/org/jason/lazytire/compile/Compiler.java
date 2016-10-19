package org.jason.lazytire.compile;

import org.jason.lazytire.compile.bean.CompileConfig;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public interface Compiler {
    void init(CompileConfig config);

    void start();

    void stop();
}
