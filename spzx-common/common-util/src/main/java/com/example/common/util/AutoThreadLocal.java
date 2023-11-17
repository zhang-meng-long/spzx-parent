package com.example.common.util;

import com.example.spzx.model.entity.system.SysUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/1 19:50
 */

public class AutoThreadLocal {

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









}
