package org.jason.lazytire.admin.filter;


import org.jason.lazy.tire.common.utils.Base64;
import org.jason.lazytire.vcs.support.gitlab.AuthInfo;
import org.jason.lazytire.vcs.support.gitlab.Authorization;

import javax.inject.Named;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Jason on 2016/10/30.
 */
@Named
public class AuthFilter implements Filter {
    private Authorization authorization;
    private ConcurrentMap<String, Long> session = new ConcurrentHashMap<String, Long>();

    public void init(FilterConfig filterConfig) throws ServletException {
        authorization = new Authorization(filterConfig.getInitParameter("vcs-host"));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorization = request.getHeader("authorization");

        if (null != authorization && authorization.length() > 6) {
            String auth = authorization.substring(6);
            String[] split = new String(Base64.decodeFast(auth)).split(":");
            AuthInfo authorize = this.authorization.authorize(split[0], split[1]);
            if (authorize.isSuccess()) {
                authenticateSuccess(response);
                filterChain.doFilter(request, response);
            } else {
                needAuthenticate(request, response);
            }
        } else {
            needAuthenticate(request, response);
        }
    }

    private void authenticateSuccess(final HttpServletResponse response) {
        response.setStatus(200);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

    private void needAuthenticate(final HttpServletRequest request, final HttpServletResponse response) {
        response.setStatus(401);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("WWW-authenticate", "Basic Realm=\"lazy tire admin need auth\"");
    }

    public void destroy() {

    }
}
