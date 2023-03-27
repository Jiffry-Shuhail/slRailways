package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name="train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please enter the Name")
    @Column(name = "name")
    private String name;

    @Column
    private String description;

    @Column
    private boolean active;

    @NotNull(message = "Please select the Departure")
    @ManyToOne
    @JoinColumn(name = "departure")
    private Station departure;

    @NotNull(message = "Please select the Arrival")
    @ManyToOne
    @JoinColumn(name = "arrival")
    private Station arrival;

    @NotEmpty(message = "Please add Stations")
    @OneToMany(mappedBy="train")
    private List<StationsDuration> stationsDurations;

    @NotEmpty(message = "Please add Classes")
    @OneToMany(mappedBy="train")
    private List<TrainHasClasses> trainHasClasses;

    @OneToMany(mappedBy="train")
    private List<TrainSchedule> trainSchedules;
}
