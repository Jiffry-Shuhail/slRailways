package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.TrainSchedule;
import com.lk.RailwayDepartment.Entity.TrainSeatPriceAndAvailability;
import com.lk.RailwayDepartment.Model.TrainScheduleListFilter;

import java.util.List;

public interface TrainScheduleService {
    void save(TrainSchedule trainSchedule);

    void saveAvailability(TrainSeatPriceAndAvailability trainSeatPriceAndAvailability);
    void update(TrainSchedule trainSchedule);
    void activateOrDeactivate(TrainSchedule trainSchedule);

    List<TrainSchedule> filter(TrainScheduleListFilter trainScheduleListFilter);

    TrainSchedule findById(long id);
}
