package com.niit.userService.UserService.services;

import com.niit.MovieService.domain.Movie;
import com.niit.userService.UserService.exception.UserAlreadyExistsException;
import com.niit.userService.UserService.exception.UserNotFoundException;
import com.niit.userService.UserService.models.User;
import com.niit.userService.UserService.proxy.UserProxy;
import com.niit.userService.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProxy userProxy;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        if(userRepository.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User savedUser = userRepository.save(user);
        if (!(savedUser.getEmail().isEmpty())){
            ResponseEntity rs = userProxy.saveUser(user);
            System.out.println(rs.getBody());
        }
        return savedUser;
    }

    @Override
    public User updateUser(String email, User user) throws UserNotFoundException {

        if(userRepository.findById(email).isEmpty()){
            throw  new UserNotFoundException();
        }
       User user1 = userRepository.findById(email).get();

       user1.setUserName(user.getUserName());
       user1.setAge(user.getAge());
       user1.setMobileNo(user.getMobileNo());
       user1.setAddress(user.getAddress());
       user1.setProfilePic(user.getProfilePic());

        return userRepository.save(user1);
    }

    @Override
    public boolean deleteUser(String email) throws UserNotFoundException {
        if (userRepository.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        userRepository.deleteById(email);
        return  true;
    }



}
