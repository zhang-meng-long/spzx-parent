package com.example.manager.service;

import com.example.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/5 14:50
 */

public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
