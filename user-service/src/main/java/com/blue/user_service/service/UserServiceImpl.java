package com.blue.user_service.service;

import com.blue.user_service.dto.UserRequestDto;
import com.blue.user_service.entity.Role;
import com.blue.user_service.entity.User;
//import com.blue.user_service.repository.RoleRepository;
import com.blue.user_service.repository.RoleRepository;
import com.blue.user_service.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserRequestDto userDto){

        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));

        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_USER");
        user.setRole(role);
        System.out.println(user);

        return  userRepository.save(user);
    }

    @Override
    public boolean loginUser (User user){
        User validateUser = userRepository.findById(user.getId()).get();
        return BCrypt.checkpw(user.getPassword(), validateUser.getPassword());

    }

    @Override
    public User getUserbyId (long userId){
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not Found"));
        }


    @Override
    public void updateUserbyId (long userId,User user){
     User foundUser=   userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not Found"));
     foundUser.setUsername(user.getUsername());
    }

    @Override
    @Transactional
    public User assignRoleToUser(long userId,String role) {
        User user=   userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not Found"));
        Role roleFound= roleRepository.findByname(role).orElseThrow(()-> new RuntimeException("Role not Found"));
        user.setRole(roleFound);
        return  userRepository.save(user);
    }
}
