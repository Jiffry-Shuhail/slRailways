package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="trainseatpriceandavailability")
public class TrainSeatPriceAndAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter the Seats Count")
    @Column
    private int seats;

    @NotNull(message = "Please enter the Available Seats Count")
    @Column
    private int availability;

    @NotNull(message = "Please enter the Seats Price")
    @Column
    private double price;

    @NotNull(message = "Please select the Class")
    @ManyToOne
    @JoinColumn(name = "trainclasses")
    private TrainClass trainClass;

    @ManyToOne
    @JoinColumn(name = "trainschedule")
    private TrainSchedule trainSchedule;

    public TrainSeatPriceAndAvailability(int seats, int availability, double price, TrainClass trainClass) {
        this.seats = seats;
        this.availability = availability;
        this.price = price;
        this.trainClass = trainClass;
    }
}
