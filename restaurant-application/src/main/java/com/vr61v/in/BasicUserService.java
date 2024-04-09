package com.vr61v.in;

import com.vr61v.models.user.User;

import java.util.UUID;

public interface BasicUserService {
    User createUser(User user);
    User getUserById(UUID userId);
    User updateUser(UUID userId, String name, String phone, String email, String password);
    UUID deleteUser(UUID userId);
}
