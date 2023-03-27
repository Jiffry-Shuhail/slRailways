package com.lk.RailwayDepartment.Service;
import com.lk.RailwayDepartment.Entity.TrainClass;

import java.util.List;

public interface ClassService {
    List<TrainClass> findAllByStatus();

    TrainClass findById(long id);
}
