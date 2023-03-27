package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("EEE, d MMM yyyy");
    private String name;
    private String mainDeparture;
    private String mainArrival;
    private String classes;
    private String seats;
    private String departure;
    private String arrival;
    private String reachTime;
    private String departureTime;
    private String duration;
    private String arrivalTime;
    private String passenger;
    private String date;
    public Ticket(Booking booking){
        this.name=booking.getTrainSchedule().getTrain().getName();
        this.mainDeparture= getPrefix(booking.getTrainSchedule().getTrain().getDeparture().getName());
        this.mainArrival= getPrefix(booking.getTrainSchedule().getTrain().getArrival().getName());
        this.classes=getPrefix(booking.getTrainClass().getName());
        this.seats= String.valueOf(booking.getPassengers());
        this.departure=  getPrefix(booking.getFrom().getName());
        this.arrival=  getPrefix(booking.getTo().getName());
        Date departure1 = booking.getTrainSchedule().getTrainDepartureArrivalTimeList().stream().filter(e -> e.getStation().equals(booking.getFrom())).findFirst().get().getDeparture();
        Date arrival1 = booking.getTrainSchedule().getTrainDepartureArrivalTimeList().stream().filter(e -> e.getStation().equals(booking.getTo())).findFirst().get().getDeparture();
        this.reachTime=SIMPLE_DATE_FORMAT.format(departure1);
        this.departureTime=this.reachTime;
        this.arrivalTime=SIMPLE_DATE_FORMAT.format(arrival1);
        this.passenger=booking.getUser()!=null?
                booking.getUser().getTitle()+". "+booking.getUser().getFname()+" "+booking.getUser().getLname()
        :booking.getContact();
        this.date=DATE_FORMAT.format(booking.getTrainSchedule().getDate());

        LocalTime big = LocalTime.parse(SIMPLE_DATE_FORMAT.format(arrival1));
        LocalTime small = LocalTime.parse(SIMPLE_DATE_FORMAT.format(departure1));

        this.duration= DateTimeFormatter.ofPattern("HH:mm").format(big.minusHours(small.getHour()).minusMinutes(small.getMinute()));
    }

    private String getPrefix(String s){
        if(s.trim().contains(" ")){
            StringBuilder prefix= new StringBuilder();
            for (String s1 : s.trim().split("\\s+")) {
                prefix.append(s1.trim().toUpperCase().charAt(0));
            }
            return prefix.toString();
        }
        return s;
    }
}
