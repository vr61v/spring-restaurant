package com.vr61v.out.user;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.user.types.Role;
import com.vr61v.models.user.User;
import com.vr61v.out.BasicRepository;

import java.util.List;

public interface Users extends BasicRepository<User> {
    User findByPhone(String phone) throws NotFoundException;
    User findByEmail(String email) throws NotFoundException;
    List<User> findByRole(Role role);
}
