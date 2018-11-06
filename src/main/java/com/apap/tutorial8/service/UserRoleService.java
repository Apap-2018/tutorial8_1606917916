package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

import java.util.Optional;

public interface UserRoleService {
    UserRoleModel addUser(UserRoleModel user);
    public String encrypt(String password);

    UserRoleModel findUserByUsername(String name);

    void changePassword(UserRoleModel user, String newPassword);
}
