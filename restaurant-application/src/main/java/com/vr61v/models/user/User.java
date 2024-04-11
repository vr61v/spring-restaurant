package com.vr61v.models.user;

import com.vr61v.models.user.types.Gender;
import com.vr61v.models.user.types.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private Calendar dateOfBirth;
    private Gender gender;
    private Role role;
}
