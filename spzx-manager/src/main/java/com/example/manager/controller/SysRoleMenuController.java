package com.example.manager.controller;

import com.example.manager.service.SysRoleMenuService;
import com.example.spzx.model.dto.system.AssginMenuDto;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/5 14:50
 */
@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    public Result  findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId){
   Map<String,Object> map=sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }





}
