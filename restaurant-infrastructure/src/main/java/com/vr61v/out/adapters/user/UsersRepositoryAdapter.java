package com.vr61v.out.adapters.user;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.user.User;
import com.vr61v.models.user.types.Role;
import com.vr61v.out.entities.user.UserEntity;
import com.vr61v.out.entities.user.mappers.UserMapper;
import com.vr61v.out.repositories.user.UsersRepository;
import com.vr61v.out.user.Users;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UsersRepositoryAdapter implements Users {
    private final UsersRepository repository;
    private final UserMapper mapper;

    @Override
    public User find(UUID id) throws NotFoundException {
        UserEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public User save(User model) {
        UserEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        UserEntity user = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with id {" + id + "} was not found."));
        repository.delete(user);
    }

    @Override
    public User findByPhone(String phone) throws NotFoundException {
        UserEntity user = repository
                .findByPhone(phone)
                .orElseThrow(() -> new NotFoundException("User with phone {" + phone + "} was not found."));
        return mapper.entityToModel(user);
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        UserEntity user = repository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email {" + email + "} was not found."));
        return mapper.entityToModel(user);
    }

    @Override
    public List<User> findByRole(Role role) {
        List<UserEntity> users = repository.findByRole(role);
        return users.stream().map(mapper::entityToModel).collect(Collectors.toList());
    }
}
