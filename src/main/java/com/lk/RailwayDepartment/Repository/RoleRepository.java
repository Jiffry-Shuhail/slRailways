package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
