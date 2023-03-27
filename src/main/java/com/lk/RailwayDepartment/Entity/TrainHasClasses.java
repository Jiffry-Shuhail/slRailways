package com.lk.RailwayDepartment.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name="trainhasclass")
public class TrainHasClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean active;

    @NotNull(message = "Please enter the Seats Count")
    @Column
    private int seats;

    @NotNull(message = "Please enter the Seats Price")
    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "train")
    private Train train;

    @NotNull(message = "Please select the Class")
    @ManyToOne
    @JoinColumn(name = "trainclasses")
    private TrainClass trainClass;
}
