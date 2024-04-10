package com.vr61v.out.product;

import com.vr61v.models.product.Product;
import com.vr61v.out.BasicRepository;

import java.util.List;

public interface Products extends BasicRepository<Product> {
    List<Product> findAll();
}
