package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.active=true")
    List<User> findAllByActive();
}
