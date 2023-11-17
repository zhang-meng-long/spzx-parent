package com.common.logs;

import com.example.spzx.model.entity.system.SysOperLog;

/**
 * @Author 张乔
 * @Date 2023/11/13 21:38
 */

public interface AsyncOperLogService {

    void saveSysOperLog(SysOperLog sysOperLog) ;

}
