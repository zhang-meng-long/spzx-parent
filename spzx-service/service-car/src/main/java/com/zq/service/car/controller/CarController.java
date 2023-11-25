package com.zq.service.car.controller;

import com.example.spzx.model.entity.h5.CartInfo;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.zq.service.car.Service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/18 15:55
 */
@RestController
@RequestMapping("/api/order/cart")
public class CarController {

@Autowired
    private CarService carService;


//这里在添加购物车时，前端应该同时传入用户的的token，我们这里获取用户的token值，得到用户信息
//    skuId，加入购物车的商品的规格
//    skuNum，商品的数量
    @Operation(summary = "添加购物车")
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@Parameter(name = "skuId", description = "商品skuId", required = true)
                                @PathVariable("skuId") Long skuId,
                            @Parameter(name = "skuNum", description = "数量", required = true)
                            @PathVariable("skuNum") Integer skuNum,
                            @RequestHeader("token") String token) {
        carService.addToCart(skuId, skuNum,token);
        return Result.success();
    }


    @Operation(summary = "查询购物车")
    @GetMapping("auth/cartList")
    public Result getList(@RequestHeader("token") String token){
     List<CartInfo> cartInfoList= carService.getList(token);
        return Result.SuccessData(cartInfoList);
    }

    @Operation(summary = "删除购物车商品")
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId,
                             @RequestHeader("token") String token) {
        carService.deleteCart(token,skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary="更新购物车商品选中状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@Parameter(name = "skuId", description = "商品skuId", required = true)
                                @PathVariable(value = "skuId") Long skuId,
                            @Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true)
                            @PathVariable(value = "isChecked") Integer isChecked,
                            @RequestHeader("token") String token) {
carService.checkCart(token,skuId,isChecked);
return Result.success();
    }

    @Operation(summary="更新购物车商品全部选中状态")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true)
                                   @PathVariable(value = "isChecked") Integer isChecked,
                               @RequestHeader("token") String token){
        carService.allCheckCart(token,isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary="清空购物车")
    @GetMapping("/auth/clearCart")
    public Result clearCart(@RequestHeader("token") String token){
        carService.clearCart(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary="选中的购物车")
    @GetMapping(value = "/auth/getAllCkecked")
    public Result<List<CartInfo>>  getAllCkecked(@RequestHeader("token") String token){
     List<CartInfo> cartInfoList=   carService.getAllCkecked(token);
        return Result.SuccessData(cartInfoList);
    }

    @GetMapping(value = "/auth/deleteChecked")
    public Result deleteChecked(@RequestHeader("token") String token) {
        carService.deleteChecked(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }


}

