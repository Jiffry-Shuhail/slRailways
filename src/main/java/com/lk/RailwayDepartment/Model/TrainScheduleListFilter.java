package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.Station;
import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Entity.TrainClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainScheduleListFilter {
    private Train train;
    private TrainClass trainClass;
    private Date fromDate;
    private Date toDate;
    private Station departure;
    private Station arrival;
    private boolean active;
    private int passengers;
}
