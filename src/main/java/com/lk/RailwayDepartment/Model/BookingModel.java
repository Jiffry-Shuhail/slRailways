package com.lk.RailwayDepartment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingModel {
    private String trainId; //TrainSchedule
    private String from;
    private String to;
    private String classId; //TrainClass
    private String contact;
    private String passengers;
    private String description;
    private String name;
    private String number;
    private String expire;
    private String csv;
}
