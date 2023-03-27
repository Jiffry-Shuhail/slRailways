package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Model.TrainListFilter;

import java.util.List;

public interface TrainService {
    void save(Train train);

    void update(Train train);

    void deactivate(Train train);
    void activate(Train train);

    Train findByName(String name);

    List<Train> filter(TrainListFilter trainListFilter);

    List<Train> findAllByActive();
}
