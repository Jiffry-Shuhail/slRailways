package com.lk.RailwayDepartment.Repository;

import com.lk.RailwayDepartment.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.active=true")
    List<Booking> findAllByActive();
}
