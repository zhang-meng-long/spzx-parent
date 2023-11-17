package com.example.manager.controller;

import com.example.manager.service.SysMenuService;
import com.example.spzx.model.entity.system.SysMenu;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/4 16:36
 */
@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

//    获取菜单数据
    @GetMapping("/findNodes")
    public Result findNodes() {
      List<SysMenu> list= sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
//新增菜单
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
//    修改菜单
    @PutMapping("/updateById")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
//    删除菜单
    @DeleteMapping("/removeById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        sysMenuService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }



}
