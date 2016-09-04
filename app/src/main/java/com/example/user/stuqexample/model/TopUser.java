package com.example.user.stuqexample.model;

import com.google.gson.annotations.Expose;

/**
 * Created by zhaibingjie on 16/8/31.
 */
public class TopUser {

    /*
    "id": "zhang-jia-wei",
    "agree": 2453439,
    "name": "张佳玮",
    "hash": "f9de84865e3e8455a09af78bfe4d1da5",
    "avatar": "https://pic2.zhimg.com/424c70919_l.jpg",
    "signature": "公众号：张佳玮写字的地方"
     */

    //此注解作用在属性上，表明当序列化和反序列化的时候，这个属性将会暴露给Gson对象。这个注解只有当创建Gson对象时使用GsonBuilder方式创建并调用了GsonBuilder.excludeFieldsWithoutExposeAnnotation() 方法的时候才有效，否则无效。

    @Expose
    public String id;
    @Expose
    public String agree;
    @Expose
    public String name;
    @Expose
    public String hash;
    @Expose
    public String avatar;
    @Expose
    public String signature;

    /*
    此注解作用在属性上，表明当序列化和反序列化的时候，这个属性将会暴露给Gson对象。
    这个注解只有当创建Gson对象时使用GsonBuilder方式创建并调用了GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
     */

    @Expose private String firstName;
    @Expose(serialize = false) private String lastName;
    @Expose (serialize = false, deserialize = false) private String emailAddress;
    private String password;

    //Gson 将会在反序列化 及 序列化 时排除掉 emailAddress 字段
}
