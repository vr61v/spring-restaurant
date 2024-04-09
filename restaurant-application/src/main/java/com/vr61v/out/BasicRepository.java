package com.vr61v.out;

import java.util.UUID;

public interface BasicRepository <T>{
    T find(UUID id);
    T save(T t);
    void delete(UUID id);
}
