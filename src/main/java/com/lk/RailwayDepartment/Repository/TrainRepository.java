package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {

    @Query("SELECT t FROM Train t WHERE t.active=true ")
    List<Train> findAllByActive();

    @Query("SELECT t FROM Train t WHERE t.name= LOWER(:name)")
    Train findByName(String name);

//    List<Train> findAllByArrivalAndAndDepartureAndStatus();

}
