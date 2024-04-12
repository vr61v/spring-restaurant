package com.vr61v.out.adapters.restaurant;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.out.entities.restaurant.RestaurantEntity;
import com.vr61v.out.entities.restaurant.mappers.RestaurantMapper;
import com.vr61v.out.repositories.restaurant.RestaurantsRepository;
import com.vr61v.out.restaurant.Restaurants;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestaurantsRepositoryAdapter implements Restaurants {
    private final RestaurantsRepository repository;
    private final RestaurantMapper mapper;

    @Override
    public Restaurant find(UUID id) throws NotFoundException {
        RestaurantEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public Restaurant save(Restaurant model) {
        RestaurantEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        RestaurantEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id {" + id + "} was not found."));
        repository.delete(entity);
    }
    @Override
    public List<Restaurant> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::entityToModel)
                .collect(Collectors.toList());
    }
}
