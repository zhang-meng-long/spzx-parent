package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author 张乔
 * @Date 2023/11/6 15:32
 */
@Mapper
public interface CategoryServiceMapper extends BaseMapper<Category> {
    @Select("select count(id) from category where parent_id = #{id} and is_deleted=0")
    Long selectCounts(Long id);
}
