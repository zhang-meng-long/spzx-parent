package com.example.manager.controller;

import com.common.logs.Log;
import com.common.logs.OperatorType;
import com.example.manager.config.PageInfoTest;
import com.example.manager.service.SysUserService;
import com.example.spzx.model.dto.system.AssginRoleDto;
import com.example.spzx.model.dto.system.SysUserDto;
import com.example.spzx.model.entity.system.SysRole;
import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 张乔
 * @Date 2023/11/3 14:58
 */
@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService ;

    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(SysUserDto sysUserDto ,
                             @PathVariable(value = "pageNum") Integer pageNum ,
                             @PathVariable(value = "pageSize") Integer pageSize){

        PageInfoTest <SysUser> pageInfoTest=sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfoTest, ResultCodeEnum.SUCCESS);


    }

    @Log(title = "新增用户了",businessType = 1)
    @PostMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }
    @PutMapping(value = "/updateSysUser")
    public Result UpdateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
//根据id删除用户
    @Log(title = "删除用户了",businessType = 2)
@DeleteMapping(value = "/deleteById/{id}")
    public Result DeleteById(@PathVariable(value = "id") Integer id){
        sysUserService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }


//    给用户分配角色
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){

        sysUserService.doAssign(assginRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);


    }




}
