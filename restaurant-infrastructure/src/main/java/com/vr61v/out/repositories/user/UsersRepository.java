package com.vr61v.out.repositories.user;

import com.vr61v.models.user.types.Role;
import com.vr61v.out.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhone(String phone);
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByRole(Role role);
}
