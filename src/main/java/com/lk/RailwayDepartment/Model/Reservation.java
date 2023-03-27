package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.Station;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation{
    @NotNull(message = "Please select the Departure")
    private Station from;
    @NotNull(message = "Please select the Arrival")
    private Station to;
    @NotNull(message = "Please select the Date")
    private Date onewayDate;
    private Date returnDate;
    private boolean returnWay;
    private boolean home;
    private int passengers;
}
