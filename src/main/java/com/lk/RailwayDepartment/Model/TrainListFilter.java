package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainListFilter {
    private String name;
    private Station departure;
    private Station arrival;
    private boolean active;
}
