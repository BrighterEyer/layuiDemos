package com.code.service;

import com.code.model.User;
import com.code.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByName(String name){
        User user = null;
        try{
            user = userRepository.findByUserName(name);
        }catch (Exception e){}
        return user;
    }

}