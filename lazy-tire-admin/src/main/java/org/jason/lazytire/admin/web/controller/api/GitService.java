package org.jason.lazytire.admin.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import org.jason.lazy.tire.common.bean.DeployConf;
import org.jason.lazytire.vcs.support.gitlab.GitlabVersionController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jason.Xia on 16/11/8.
 */
@Controller
@RequestMapping(value = "/api", produces = "text/html;charset=UTF-8")
public class GitService {
    private GitlabVersionController vcs = new GitlabVersionController();

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    @ResponseBody
    public String getProjects(HttpServletRequest request) {
        try {
            return JSONObject.toJSONString(this.vcs.getProjects(request.getHeader("authorization")));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/project/branches", method = RequestMethod.POST)
    @ResponseBody
    public String getBranches(int id, HttpServletRequest request) {
        try {
            return JSONObject.toJSONString(this.vcs.listBranches(request.getHeader("authorization"), id));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/branch/deploy", method = RequestMethod.POST)
    @ResponseBody
    public String compileBranche(DeployConf config, HttpServletRequest request) {

    }

}
