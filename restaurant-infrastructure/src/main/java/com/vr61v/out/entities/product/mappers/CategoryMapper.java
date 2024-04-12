package com.vr61v.out.entities.product.mappers;

import com.vr61v.models.product.Category;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.product.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryEntity, Category> { }