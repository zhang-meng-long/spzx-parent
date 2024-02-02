package com.zq.service.user.controller;

import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.vo.common.Result;
import com.zq.service.user.service.RegistryService;
import com.zq.service.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/28 17:48
 */

@RestController

public class Test {
    @Autowired
    private RegistryService registryService;
    //    用户浏览过的商品数据（我这里只做一个假商品数据）

@GetMapping("api/user/userInfo/auth/findUserBrowseHistoryPage/{page}/{size}")
    public Result get(){
    System.err.println("地方大师傅");
    List<Product> list = registryService.getPage();
    return Result.SuccessData(list);

}



}
