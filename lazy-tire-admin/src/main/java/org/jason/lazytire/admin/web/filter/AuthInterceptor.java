package org.jason.lazytire.admin.web.filter;

import org.jason.lazytire.admin.access.mapper.AuthMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jason.Xia on 16/11/5.
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Inject
    private AuthMapper authMapper;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        if (uri.endsWith("login")) {
            return true;
        }

        String authorization = httpServletRequest.getHeader("authorization");
        if (1 == authMapper.validPrivateToken(authorization)) {
            return true;
        }

        httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
