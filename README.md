微服务的概念最近几年越来越火，目前有不少框架适合用于搭建微服务，Spring Boot应该是不少公司的选择（因为不少公司原来就是用Spring的，改造成本较低）。

微服务对外开放接口一般都是通过Restful API的方式，如何及时维护API文档是一件费时费力的事情（主要是不是码农没有及时维护文档的习惯），通过Swagger与SpringBoot整合，可以方便搭建一个Restful API项目，并自动生成对应的API文档。

*PS：有些人说Swagger需要通过注解的方式定义相关的接口描述信息，代码侵入性很强，这个个人认为没什么意义，因为理论上来说，你调整了一个接口的实现，本来就是应该将接口描述进行调整。而且已经开放的接口接口协议调整的几率是比较小的。*


### 1.创建项目
创建Maven项目，配置Spring Boot和Swagger相关项目依赖。

**pom.xml:**
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.cent.demo</groupId>
    <artifactId>spring-boot-swagger-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.1.RELEASE</version>
    </parent>

    <properties>
        <spring.boot.version>1.4.1.RELEASE</spring.boot.version>
        <springfox.version>2.6.0</springfox.version>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot web starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Swagger2核心包-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${springfox.version}</version>
        </dependency>
        <!-- Swagger2 UI包，前端展示API文档 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            <!-- spring热部署配置 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.plateno.perpareBooking.PlatenoHissPerpareBookingBootApplication</mainClass>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.springframework</groupId>
                        <artifactId>springloaded</artifactId>
                        <version>1.2.5.RELEASE</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
</project>
```


### 2.Swagger2配置

创建SwaggerConfig类，通过Spring的javaConfig方式定义Swagger的Bean。可定义多个Docket并配置不同的扫描路径，以实现Api接口的归类。

**SwaggerConfig.class：**

```
package org.cent.demo.spring_boot_swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * Created by cent on 2016/10/31.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /**
     * swagger摘要bean
     * @return
     */
    @Bean
    public Docket restApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.cent.demo.spring_boot_swagger.controller"))
                .paths(PathSelectors.any())
                .build()
                ;

        return docket;
    }

    /**
     * API文档主信息对象
     * @return
     */
    private ApiInfo apiInfo(){
        ApiInfo apiInfo= (new ApiInfoBuilder())
                .title("Spring Boot集成Swagger项目")
                .description("Spring Boot集成Swagger的Demo API")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact(new Contact("cent","","292462859@qq.com"))
                .version("1.0")
                .build();
        return apiInfo;
    }
}

```


### 3.定义API

本示例定义了两个接口，都是查询用户信息接口，一个是通过URI传参的方式，另外一个是通过querystring传参的方式，具体如下：

- 用户实体对象-User.class:

```
package org.cent.demo.spring_boot_swagger.entity;

import java.io.Serializable;

/**
 * 用户对象
 * Created by cent on 2016/10/31.
 */
public class User implements Serializable{

    private String id;

    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}

```

- 用户对象业务服务接口-IUserService.class:

```
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

```

- 用户对象业务服务接口实现类-UserServiceImpl.class:

```
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

```

- 用户操作Restful API入口-UserController.class:

```
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
    @ResponseBody
    public User queryUser1(@PathVariable String id) {
        User user = userService.queryUserById(id);
        return user;
    }


    @ApiOperation(value = "获取用户详情2", notes = "根据用户ID获取用户详情信息，通过querystring传参")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public User queryUser2(@RequestParam String id) {
        User user = userService.queryUserById(id);
        return user;
    }
}

```

### 4.启动，大功告成

启动SpringBoot，浏览器访问 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 链接，可看到对应Swagger生成的API文档，并且可以在页面中进行接口测试。

*原生的Swagger-UI的API文档在公司内部应该可以直接使用了，不过需要发布给外部使用的话，可能不太合适，但可以对swagger-ui的相关样式/文字进行修改，调整到适合发布即可。*

![image](http://note.youdao.com/yws/public/resource/8d78f6e2e9a27131633f106edbeeee9e/xmlnote/A9F45735877E426EB66034D4BD1B304C/29687)


### 5.示例源码地址

阿里Code源码下载地址：[https://code.aliyun.com/cent/spring-boot-swagger-demo.git](https://code.aliyun.com/cent/spring-boot-swagger-demo.git)