package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="traindeparturearrivaltime")
public class TrainDepartureArrivalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station")
    private Station station;

    @NotNull(message = "Please enter the Arrival") @DateTimeFormat(pattern = "HH:mm:ss")
    @Column
    private Date arrival;

    @NotNull(message = "Please enter the Departure") @DateTimeFormat(pattern = "HH:mm:ss")
    @Column
    private Date departure;

    @ManyToOne
    @JoinColumn(name = "trainschedule")
    private TrainSchedule trainSchedule;

    public TrainDepartureArrivalTime(Station station, Date arrival, Date departure) {
        this.station = station;
        this.arrival = arrival;
        this.departure = departure;
    }
}
