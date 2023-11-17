package com.example.manager.config;

import com.example.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/5 12:57
 */

public class MenuHelper {


    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> BuildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> trees = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId() == 0) {
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu it : treeNodes) {
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                //if (sysMenu.getChildren() == null) {
                //    sysMenu.setChildren(new ArrayList<>());
                //}
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }























////    使用递归方法建菜单
//public static List<SysMenu> BuildTree(List<SysMenu> menuList) {
//    List<SysMenu> list = new ArrayList<>();
//
//    for (SysMenu sysMenu : menuList) {
//        if (sysMenu.getParentId() == 0) {
//            list.add(Children(list, sysMenu));
//        }
//    }
//    return list;
//}
//// 递归查找子节点
//    public static SysMenu Children(List<SysMenu> list,SysMenu sysMenu) {
//        sysMenu.setChildren(new ArrayList<>());
//        for (SysMenu it : list) {
//            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
//                //if (sysMenu.getChildren() == null) {
//                //    sysMenu.setChildren(new ArrayList<>());
//                //}
//                sysMenu.getChildren().add(Children(list, it));
//            }
//        }
//        return sysMenu;
//    }
}
