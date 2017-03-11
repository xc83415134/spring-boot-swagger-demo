package org.cent.demo.spring_boot_swagger.service.impl;

import org.cent.demo.spring_boot_swagger.entity.User;
import org.cent.demo.spring_boot_swagger.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 用户对象业务服务接口实现类
 * Created by cent on 2016/10/31.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public User queryUserById(String id) {

        User user=null;
        if("1".equals(id)){
            user=new User("1","test_user");
        }

        return user;
    }
}
