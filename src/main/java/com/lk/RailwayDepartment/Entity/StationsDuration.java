package com.lk.RailwayDepartment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stationduration")
public class StationsDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station")
    private Station station;

    @NotNull(message = "Please enter the Duration") @DateTimeFormat(pattern = "HH:mm:ss")
    @Column
    private Date duration;

    @ManyToOne
    @JoinColumn(name = "train")
    private Train train;
}
