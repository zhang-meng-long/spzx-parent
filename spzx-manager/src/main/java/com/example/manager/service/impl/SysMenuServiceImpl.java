package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zq.common.service.exception.CustomException;
import com.example.common.util.AutoThreadLocal;
import com.example.manager.config.MenuHelper;
import com.example.manager.mapper.SysMenuMapper;
import com.example.manager.service.SysMenuService;
import com.example.spzx.model.entity.system.SysMenu;
import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.example.spzx.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/4 16:37
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public List<SysMenu> findNodes() {
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(SysMenu::getSortValue);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(lambdaQueryWrapper);

//      List<SysMenu> sysMenus=  sysMenuMapper.selectLists();
        List<SysMenu> sysMenuList = MenuHelper.BuildTree(sysMenus);

        return sysMenuList;
    }

    @Override
    public void save(SysMenu sysMenu) {
//        sysMenuMapper.inserts(sysMenu);
        sysMenuMapper.insert(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) {
        sysMenu.setUpdateTime(new Date());

//        sysMenuMapper.updateByIds(sysMenu);
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void deleteById(Long id) {
//        先判断是不是子菜单。如果是子菜单可以删除，否则不能进行删除
    QueryWrapper<SysMenu> lambdaQueryWrapper= new QueryWrapper<>();
        lambdaQueryWrapper.select("count(id)");
        lambdaQueryWrapper.eq("parent_id",id);
//        int delete = sysMenuMapper.deletes(id);
        int delete = sysMenuMapper.delete(lambdaQueryWrapper);
        if (delete>0){
            throw new CustomException(ResultCodeEnum.NODE_ERROR);
        }

//         sysMenuMapper.deleteByIds(id);
         sysMenuMapper.deleteById(id);



    }

    @Override
    public List<SysMenuVo> menus() {
//        从当前线程中获取用户信息
        SysUser user = AutoThreadLocal.getUser();
        Long userId = user.getId();

//        根据当前登陆的用户Id，查询处它能操作的菜单。
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId) ;

//        已经通过递归来实现菜单的分层
        List<SysMenu> sysMenuTreeList = MenuHelper.BuildTree(sysMenuList);

//        将菜单集合转化为能返回给前端的数据格式。
        return buildMenus(sysMenuTreeList);

    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }




}
