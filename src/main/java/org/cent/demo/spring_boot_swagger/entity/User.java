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
