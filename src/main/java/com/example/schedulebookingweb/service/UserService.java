package com.example.schedulebookingweb.service;

import com.example.schedulebookingweb.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void save(User user);
    User findById(long id);
    void delete(long id);
    List<User> findByName(String name);
    User findByEmail(String email);
}
