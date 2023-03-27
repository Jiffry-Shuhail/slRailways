package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Station;

import java.util.List;

public interface StationService {
    List<Station> findAllByActive();

    Station findById(long id);
}
