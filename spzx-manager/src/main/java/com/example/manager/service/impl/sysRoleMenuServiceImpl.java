package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.manager.mapper.RoleMenuMapper;
import com.example.manager.mapper.SysMenuMapper;
import com.example.manager.service.SysMenuService;
import com.example.manager.service.SysRoleMenuService;
import com.example.spzx.model.dto.system.AssginMenuDto;
import com.example.spzx.model.entity.system.SysMenu;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/5 14:50
 */
@Service
public class sysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysMenuService sysMenuService;

@Autowired
  private   RoleMenuMapper roleMenuMapper;


    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        // 查询所有的菜单数据
        List<SysMenu> menuList = sysMenuService.findNodes();

        List<Long> longList=   roleMenuMapper.RoleMenuByRoleId(roleId);


        System.err.println(longList.toString());
//        System.out.println(longList.get(0));

Map<String,Object> map=new HashMap<>();

map.put("sysMenuList",menuList);
map.put("roleMenuIds",longList);

        return map;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        // 根据角色的id删除其所对应的菜单数据
        roleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
//分配角色
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        System.out.println(menuIdList.toString());
        for (Map<String, Number> menuId : menuIdList) {

//            System.err.println( menuId.get("id").longValue());
         Long id=   menuId.get("id").longValue();

            roleMenuMapper.insert(assginMenuDto.getRoleId(),id);


        }



    }
}
