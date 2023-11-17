package com.example.manager.service;

import com.example.manager.config.PageInfoTest;
import com.example.spzx.model.dto.system.SysRoleDto;
import com.example.spzx.model.entity.system.SysRole;

import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/1 22:02
 */

public interface SysRoleService {
    PageInfoTest<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteByid(Integer roleId);

    Map<String, Object> findAllRoles(Long UserId);
}
