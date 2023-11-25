package com.example.common.util;

import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.entity.user.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/1 19:50
 */

public class AutoThreadLocal {

//    存储用户信息
private static ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();
//private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

//添加信息
    public static void setUser(SysUser user){

        threadLocal.set(user);
    }
//获取信息
    public static SysUser getUser(){
        return threadLocal.get();
    }
//删除信息
    public static void removeUser(){
        threadLocal.remove();
    }



//    存储UserInfo的信息
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;
    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }






}
