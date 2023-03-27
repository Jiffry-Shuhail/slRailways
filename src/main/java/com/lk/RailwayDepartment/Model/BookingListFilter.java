package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.Station;
import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Entity.TrainClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingListFilter {
    private Train train;
    private TrainClass trainClass;
    private Station departure;
    private Station arrival;
    private String fromDate;
    private String toDate;
    private boolean active;

    public BookingListFilter(boolean active) {
        this.active = active;
    }
}
