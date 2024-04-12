package com.vr61v.out.product;

import com.vr61v.models.product.Category;
import com.vr61v.out.BasicRepository;

import java.util.List;

public interface Categories extends BasicRepository<Category> {
    List<Category> findAll();
}
