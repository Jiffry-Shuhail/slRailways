package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.StationsDuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationsDurationRepository extends JpaRepository<StationsDuration, Long> {
    List<StationsDuration> findAll();
}
