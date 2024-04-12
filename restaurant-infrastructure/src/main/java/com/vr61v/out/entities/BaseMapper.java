package com.vr61v.out.entities;

public interface BaseMapper<Entity, Model> {
    Model entityToModel(Entity entity);
    Entity modelToEntity(Model model);
}