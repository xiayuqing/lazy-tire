package org.jason.lazy.tire.common;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class ApplicationContextBuilder {
    private AppContext context;


    public AppContext build(ContextConfiguration config) {
        if (null == context) {
            synchronized (this) {
                if (null == context) {
                    context = new AppContext(config);
                }
            }
        }

        return context;
    }
}
