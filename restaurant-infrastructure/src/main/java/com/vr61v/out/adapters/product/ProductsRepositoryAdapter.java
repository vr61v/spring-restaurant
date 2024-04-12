package com.vr61v.out.adapters.product;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.product.Product;
import com.vr61v.out.entities.product.ProductEntity;
import com.vr61v.out.entities.product.mappers.ProductMapper;
import com.vr61v.out.product.Products;
import com.vr61v.out.repositories.product.ProductsRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductsRepositoryAdapter implements Products {
    private final ProductsRepository repository;
    private final ProductMapper mapper;


    @Override
    public Product find(UUID id) throws NotFoundException {
        ProductEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public Product save(Product model) {
        ProductEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        ProductEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id {" + id + "} was not found."));
        repository.delete(entity);
    }

    @Override
    public List<Product> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::entityToModel)
                .collect(Collectors.toList());
    }
}
