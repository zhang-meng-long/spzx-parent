package com.example.manager.service;

import com.example.manager.config.PageInfoTest;
import com.example.spzx.model.dto.system.AssginRoleDto;
import com.example.spzx.model.dto.system.LoginDto;
import com.example.spzx.model.dto.system.SysUserDto;
import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.vo.system.LoginVo;

/**
 * @Author 张乔
 * @Date 2023/10/31 16:25
 */

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfoTest<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Integer id);

    void doAssign(AssginRoleDto assginRoleDto);
}
