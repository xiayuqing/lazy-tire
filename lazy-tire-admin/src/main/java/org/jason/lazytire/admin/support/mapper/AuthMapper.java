package org.jason.lazytire.admin.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.jason.lazytire.admin.support.domain.Auth;

/**
 * Created by Jason.Xia on 16/10/31.
 */
public interface AuthMapper {

    int insertAccountInfo(@Param("account") String account, @Param("password") String password, @Param
            ("privateToken") String privateToken);

    int insertAuth(@Param("auth") Auth auth);

    long selectSessionExpire(String session);

    int validAccountPasswd(String account, String password);
}
