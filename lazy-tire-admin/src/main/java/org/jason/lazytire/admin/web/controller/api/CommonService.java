package org.jason.lazytire.admin.web.controller.api;

import org.jason.lazy.tire.common.utils.Base64;
import org.jason.lazytire.admin.access.mapper.AuthMapper;
import org.jason.lazytire.vcs.support.gitlab.AuthInfo;
import org.jason.lazytire.vcs.support.gitlab.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Jason.Xia on 16/11/5.
 */
@Controller
@RequestMapping(value = "/api", produces = "text/html;charset=UTF-8")
public class CommonService {

    @Inject
    private AuthMapper authMapper;

    private Authorization authorization = new Authorization("http://172.16.61.211/");

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String auth = request.getHeader("authorization");
        if (null == auth) {
            response.setStatus(401);
            return null;
        }

        String[] split = new String(Base64.decodeFast(auth)).split(":");
        if (2 != split.length) {
            response.setStatus(401);
            return null;
        }

        List<String> strings = authMapper.validAccountPasswd(split[0], split[1]);
        if (null == strings || 1 != strings.size()) {
            AuthInfo result = authorization.authorize(split[0], split[1]);
            if (result.isSuccess()) {
                authMapper.insertAccountInfo(split[0], split[1], result.getPrivateToken());
                return result.getPrivateToken();
            } else {
                response.setStatus(401);
                return null;
            }
        }

        return strings.get(0);
    }

}
