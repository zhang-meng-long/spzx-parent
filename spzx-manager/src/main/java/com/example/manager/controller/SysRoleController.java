package com.example.manager.controller;

import com.example.manager.config.PageInfoTest;
import com.example.manager.service.SysRoleService;
import com.example.spzx.model.dto.system.SysRoleDto;
import com.example.spzx.model.entity.system.SysRole;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/1 22:00
 */

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService ;

//    查询所有角色
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long  userId ) {
        Map<String, Object> resultMap = sysRoleService.findAllRoles(userId);
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }











//数据列表
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@RequestBody SysRoleDto sysRoleDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize) {

//        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto , pageNum , pageSize) ;
//        String jsonString = JSON.toJSONString(pageInfo);
//        System.out.println(pageInfo);
//        System.err.println(jsonString);
        PageInfoTest<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto , pageNum , pageSize) ;

        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

//    添加用户
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);


    }
//    修改用户信息
    @PutMapping(value = "/updateSysRole")
public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
//    删除用户
    @DeleteMapping(value = "/deleteById/{roleId}")
public Result deleteById(@PathVariable(value = "roleId") Integer roleId){
        sysRoleService.deleteByid(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}