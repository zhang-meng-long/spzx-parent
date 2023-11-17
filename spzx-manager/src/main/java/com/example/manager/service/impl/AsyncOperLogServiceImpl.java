package com.example.manager.service.impl;

import com.common.logs.AsyncOperLogService;
import com.example.manager.mapper.SysOperLogMapper;
import com.example.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author 张乔
 * @Date 2023/11/13 21:40
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Async      // 异步执行保存日志操作
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.inserts(sysOperLog);
    }
}
