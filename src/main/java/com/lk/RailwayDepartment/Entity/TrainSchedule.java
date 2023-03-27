package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="trainschedule")
public class TrainSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please select the train")
    @ManyToOne
    @JoinColumn(name = "train")
    private Train train;

    @Column
    private String description;

    @Column
    private boolean active;

    @NotNull(message = "Please enter the date")
    @Column
    private Date date;

    @NotEmpty(message = "Please add Stations")
    @OneToMany(mappedBy="trainSchedule")
    private List<TrainDepartureArrivalTime> trainDepartureArrivalTimeList;

    @OneToMany(mappedBy="trainSchedule")
    private List<TrainSeatPriceAndAvailability> trainSeatPriceAndAvailabilities;

    @Transient
    private boolean valid;

    @OneToMany(mappedBy="trainSchedule")
    private List<Booking> bookingList;
}
