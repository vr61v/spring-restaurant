package com.vr61v.out.entities.product.mappers;

import com.vr61v.models.product.Product;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.product.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper extends BaseMapper<ProductEntity, Product> { }