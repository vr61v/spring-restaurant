package com.vr61v.out.product;

import com.vr61v.models.product.ProductCategory;
import com.vr61v.out.BasicRepository;

import java.util.List;

public interface ProductCategories  extends BasicRepository<ProductCategory> {
    List<ProductCategory> findAll();
}
