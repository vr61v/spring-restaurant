package com.vr61v.out;

import com.vr61v.exceptions.NotFoundException;

import java.util.UUID;

public interface BasicRepository <T> {
    T find(UUID id) throws NotFoundException;
    T save(T t);
    void delete(UUID id) throws NotFoundException;
}
