package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 张乔
 * @Date 2023/11/13 21:47
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    int inserts(SysOperLog sysOperLog);
}

