package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contact;

    @Column
    private int passengers;

    @Column
    private double price;

    @Column
    private String description;

    @Column
    private boolean active;

    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trainschedule")
    private TrainSchedule trainSchedule;

    @ManyToOne
    @JoinColumn(name = "trainclass")
    private TrainClass trainClass;

    @ManyToOne
    @JoinColumn(name = "departure")
    private Station from;

    @ManyToOne
    @JoinColumn(name = "arrival")
    private Station to;
}
