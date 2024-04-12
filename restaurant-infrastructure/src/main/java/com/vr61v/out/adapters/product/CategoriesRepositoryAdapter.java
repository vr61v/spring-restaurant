package com.vr61v.out.adapters.product;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.product.Category;
import com.vr61v.out.entities.product.CategoryEntity;
import com.vr61v.out.entities.product.mappers.CategoryMapper;
import com.vr61v.out.product.Categories;
import com.vr61v.out.repositories.product.CategoriesRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoriesRepositoryAdapter implements Categories {
    private final CategoriesRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Category find(UUID id) throws NotFoundException {
        CategoryEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public Category save(Category model) {
        CategoryEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        CategoryEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id {" + id + "} was not found."));
        repository.delete(entity);
    }

    @Override
    public List<Category> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::entityToModel)
                .collect(Collectors.toList());
    }
}
