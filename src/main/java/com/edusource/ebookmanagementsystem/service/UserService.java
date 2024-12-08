package com.edusource.ebookmanagementsystem.service;

import com.edusource.ebookmanagementsystem.dto.LoginRequest;
import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.model.User;

public interface UserService {

    Response register(User user);

    Response login(LoginRequest userLoginRequest);

    Response getAllUsers();

    Response deleteUser(String userId);

    Response getReadingHistory(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String userId);

}
