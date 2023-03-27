package com.lk.RailwayDepartment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatAvailableModel {
    private int seats;
    private int availability;
    private double price;
    private long classId;
    private String className;
}
