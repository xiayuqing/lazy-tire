package org.jason.lazytire.admin.access.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Jason.Xia on 16/10/31.
 */
public interface AuthMapper {

    int insertAccountInfo(@Param("account") String account, @Param("password") String password, @Param
            ("privateToken") String privateToken);

    List<String> validAccountPasswd(@Param("account") String account,@Param("password") String password);

    int validPrivateToken(@Param("privateToken") String privetToken);
}
