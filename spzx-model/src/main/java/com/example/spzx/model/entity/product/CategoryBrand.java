package com.example.spzx.model.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分类品牌实体类")
public class CategoryBrand extends BaseEntity {

	@Schema(description = "品牌id")
	private Long brandId;

	@Schema(description = "分类id")
	private Long categoryId;

	@TableField(exist = false)
	@Schema(description = "分类名称" , required = false)
	private String categoryName;

	@TableField(exist = false)
	@Schema(description = "品牌名称" , required = false)
	private String brandName;

	@TableField(exist = false)
	@Schema(description = "品牌logo" , required = false)
	private String logo;

}