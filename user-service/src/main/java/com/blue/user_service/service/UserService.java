package com.blue.user_service.service;

import com.blue.user_service.dto.UserRequestDto;
import com.blue.user_service.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User registerUser(UserRequestDto userDto);
    public boolean loginUser (User user);
    public User getUserbyId (long userId);
    public void updateUserbyId (long userId,User user);
    public User assignRoleToUser (long userId,String role);
}
