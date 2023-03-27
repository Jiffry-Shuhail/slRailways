package com.lk.RailwayDepartment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationModel {
    private String from;
    private String to;
    private String date;
    private String passengers;
}
