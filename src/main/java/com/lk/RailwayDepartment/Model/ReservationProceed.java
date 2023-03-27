package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationProceed {
    private TrainSchedule trainSchedule;
    private Station from;
    private Station to;
    private Date onewayDate;
    private Date returnDate;
    private boolean returnWay;
    private boolean home;
    private int passengers;
    private TrainClass trainClass;
    private String contact;
    private TrainDepartureArrivalTime trainDepartureArrivalTime;

    public ReservationProceed(Reservation reservation){
        this.from=reservation.getFrom();
        this.to=reservation.getTo();
        this.onewayDate=reservation.getOnewayDate();
        this.returnDate=reservation.getReturnDate();
        this.returnWay=reservation.isReturnWay();
        this.home=reservation.isHome();
        this.passengers=reservation.getPassengers();
    }

    @Override
    public String toString() {
        return "ReservationProceed{" +
                "trainSchedule=" + trainSchedule +
                ", from=" + from +
                ", to=" + to +
                ", onewayDate=" + onewayDate +
                ", returnDate=" + returnDate +
                ", returnWay=" + returnWay +
                ", home=" + home +
                ", passengers=" + passengers +
                ", trainClass=" + trainClass +
                ", contact='" + contact + '\'' +
                ", trainDepartureArrivalTime=" + trainDepartureArrivalTime +
                '}';
    }
}
