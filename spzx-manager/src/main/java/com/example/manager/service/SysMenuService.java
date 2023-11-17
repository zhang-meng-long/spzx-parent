package com.example.manager.service;

import com.example.spzx.model.entity.system.SysMenu;
import com.example.spzx.model.vo.system.SysMenuVo;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/4 16:37
 */

public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void deleteById(Long id);

    List<SysMenuVo> menus();
}
