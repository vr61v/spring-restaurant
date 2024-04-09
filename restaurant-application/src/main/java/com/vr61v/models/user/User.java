package com.vr61v.models.user;

import java.util.Calendar;
import java.util.UUID;

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
