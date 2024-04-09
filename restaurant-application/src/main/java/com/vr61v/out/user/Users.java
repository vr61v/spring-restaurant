package com.vr61v.out.user;

import com.vr61v.models.user.Role;
import com.vr61v.models.user.User;
import com.vr61v.out.BasicRepository;

import java.util.List;

public interface Users extends BasicRepository<User> {
    User findByPhone(String phone);
    User findByEmail(String email);
    List<User> findByRole(Role role);
}
