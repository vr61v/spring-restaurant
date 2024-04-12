package com.vr61v.out.repositories.product;

import com.vr61v.out.entities.product.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriesRepository extends JpaRepository<CategoryEntity, UUID> { }
