package org.cent.demo.spring_boot_swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.cent.demo.spring_boot_swagger.entity.User;
import org.cent.demo.spring_boot_swagger.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息操作相关API接口类
 * Created by cent on 2016/10/31.
 */
@RestController
@RequestMapping(value = "/user")
@Api(description = "用户信息操作相关API")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取用户详情1", notes = "根据用户ID获取用户详情信息，通过URL传参。")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public User queryUser1(@PathVariable String id) {
        User user = userService.queryUserById(id);
        return user;
    }


    @ApiOperation(value = "获取用户详情2", notes = "根据用户ID获取用户详情信息，通过querystring传参")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public User queryUser2(@RequestParam String id) {
        User user = userService.queryUserById(id);
        return user;
    }
}
