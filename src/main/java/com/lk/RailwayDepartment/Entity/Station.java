package com.lk.RailwayDepartment.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @Column
    private boolean active;

    @OneToMany(mappedBy="departure")
    private List<Train> departureTrains;

    @OneToMany(mappedBy="arrival")
    private List<Train> arrivalTrains;

    @OneToMany(mappedBy="station")
    private List<StationsDuration> stationsDurations;

    @OneToMany(mappedBy="station")
    private List<TrainDepartureArrivalTime> trainDepartureArrivalTimes;

    @OneToMany(mappedBy="from")
    private List<Booking> fromBookingList;

    @OneToMany(mappedBy="to")
    private List<Booking> toBookingList;
}
