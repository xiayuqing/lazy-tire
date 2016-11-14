package org.jason.lazytire.admin.support;

import org.jason.lazy.tire.common.AppContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Jason.Xia on 16/11/14.
 */
public class SystemListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String configPath = servletContextEvent.getServletContext().getInitParameter("lazytire.admin.config.path");
        System.out.println("lazytire.admin.config.path : " + configPath);

        AppContext.load(configPath);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
