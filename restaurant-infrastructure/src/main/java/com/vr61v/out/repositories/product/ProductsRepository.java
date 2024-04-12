package com.vr61v.out.repositories.product;

import com.vr61v.out.entities.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> { }
