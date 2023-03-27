package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {
    @Query("SELECT s FROM Station s WHERE s.active=true ")
    List<Station> findAllByActive();
}
