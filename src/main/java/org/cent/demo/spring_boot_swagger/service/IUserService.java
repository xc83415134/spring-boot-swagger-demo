package org.cent.demo.spring_boot_swagger.service;

import org.cent.demo.spring_boot_swagger.entity.User;

/**
 * 用户对象业务服务接口
 * Created by cent on 2016/10/31.
 */
public interface IUserService {

    /**
     *
     * @param id
     * @return
     */
    User queryUserById(String id);
}
