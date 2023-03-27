package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TrainScheduleRepository  extends JpaRepository<TrainSchedule, Long> {
}
