package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.User;
import com.lk.RailwayDepartment.Model.SubscribersListFilter;

import java.util.List;

public interface UserService {
    void saveUser(User userDto);

    User findUserByEmail(String email);

    List<User> findAllActiveUsers();
    List<User> filter(SubscribersListFilter subscribersListFilter);
}
