package org.jason.lazytire.vcs.support.gitlab;

import com.alibaba.fastjson.JSONObject;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabSession;

import java.io.IOException;

/**
 * Created by Jason.Xia on 16/10/19.
 */
public class Authorization {
    public static String HOST;

    public Authorization(String host) {
        HOST = host;
    }

    public static void main(String[] args) {
        AuthInfo authInfo = new Authorization("http://172.16.61.211/").authorize("yuqing.xia", "xia1994");
    }

    public AuthInfo authorize(String account, String password) {
        AuthInfo authInfo = new AuthInfo();
        try {
            GitlabSession connect = GitlabAPI.connect(HOST, account, password);
            authInfo.setPrivateToken(connect.getPrivateToken());
            authInfo.setMsg("success");
            authInfo.setSuccess(true);
        } catch (IOException e) {
            String message = e.getMessage();
            JSONObject jsonObject = JSONObject.parseObject(message);
            String str = jsonObject.getString("message");
            authInfo.setSuccess(false);
            authInfo.setMsg(str.substring(4));
            authInfo.setCode(str.substring(0, 3));
        }

        return authInfo;
    }
}
