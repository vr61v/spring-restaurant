package com.vr61v.out.entities.user.mappers;

import com.vr61v.models.user.User;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserEntity, User> { }