package com.vr61v.in;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.user.User;

import java.util.UUID;

public interface BasicUserService {
    User createUser(User user);
    User getUserById(UUID userId) throws NotFoundException;
    User updateUser(UUID userId, String name, String phone, String email) throws NotFoundException;
    UUID deleteUser(UUID userId) throws NotFoundException;
}
