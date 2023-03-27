package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.TrainClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassRepository extends JpaRepository<TrainClass,Long> {
    @Query("SELECT t FROM TrainClass t WHERE t.active=true ")
    List<TrainClass> findAllByActive();
}
