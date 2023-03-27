package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="trainclass")
public class TrainClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter the Name")
    @Column(nullable=false, unique=true)
    private String name;

    @Column
    private boolean active;

    @OneToMany(mappedBy="trainClass")
    private List<TrainHasClasses> trainHasClasses;

    @OneToMany(mappedBy="trainClass")
    private List<TrainSeatPriceAndAvailability> trainSeatPriceAndAvailabilities;

    @OneToMany(mappedBy = "trainClass")
    private List<Booking> bookingList;
}
