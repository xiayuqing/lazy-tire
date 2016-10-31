package org.jason.lazytire.admin.filter;


import org.jason.lazytire.admin.support.mapper.AuthMapper;

import javax.inject.Inject;
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

    @Inject
    private AuthMapper authMapper;

    private ConcurrentMap<String, Long> session = new ConcurrentHashMap<String, Long>();

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorization = request.getHeader("authorization");

        if (null != authorization) {
            if (session.containsKey(authorization)) {
                if (System.currentTimeMillis() < session.get(authorization)) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    session.remove(authorization);
                    needAuthenticate(request, response);
                }
            }
            long l = this.authMapper.selectSessionExpire(authorization);
            if (0 == l || System.currentTimeMillis() > l) {
                needAuthenticate(request, response);
            } else {
                authenticateSuccess(response);
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
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("WWW-authenticate", "Realm=\"lts admin need auth\"");
    }

    public void destroy() {

    }
}
