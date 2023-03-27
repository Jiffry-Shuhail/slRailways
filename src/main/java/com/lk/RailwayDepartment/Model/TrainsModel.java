package com.lk.RailwayDepartment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainsModel{
    private String from;
    private String to;
    private String date;
    private int passengers;
    private long trainId;
    private String trainName;
    private List<SeatAvailableModel> seatAvailableModels;
}
