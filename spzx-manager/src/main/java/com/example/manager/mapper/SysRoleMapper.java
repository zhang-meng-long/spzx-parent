package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.dto.system.SysRoleDto;
import com.example.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/1 22:03
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
}







