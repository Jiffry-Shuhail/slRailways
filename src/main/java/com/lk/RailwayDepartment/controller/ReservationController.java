package com.lk.RailwayDepartment.controller;


import com.lk.RailwayDepartment.Entity.Booking;
import com.lk.RailwayDepartment.Entity.TrainSeatPriceAndAvailability;
import com.lk.RailwayDepartment.Entity.User;
import com.lk.RailwayDepartment.Model.Reservation;
import com.lk.RailwayDepartment.Model.ReservationProceed;
import com.lk.RailwayDepartment.Model.Ticket;
import com.lk.RailwayDepartment.Model.TrainScheduleListFilter;
import com.lk.RailwayDepartment.Service.BookingService;
import com.lk.RailwayDepartment.Service.StationService;
import com.lk.RailwayDepartment.Service.TrainScheduleService;
import com.lk.RailwayDepartment.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class ReservationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private TrainScheduleService trainScheduleService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/reservation")
    private String reservation(@Valid Reservation reservation, Model model, BindingResult result) {
        if (reservation.isReturnWay() && reservation.getReturnDate() == null) {
            result.rejectValue("returnDate", null, "Please select Return Date");
        }
        reservation.setHome(false);
        model.addAttribute("stations", stationService.findAllByActive());
        model.addAttribute("reservationProceed", new ReservationProceed(reservation));


        if (result.hasErrors()) {
            model.addAttribute("services", PageController.SERVICES);
            return reservation.isHome() ? "index" : "reservation";
        }

        TrainScheduleListFilter trainScheduleListFilter = new TrainScheduleListFilter();
        trainScheduleListFilter.setActive(true);
        trainScheduleListFilter.setArrival(reservation.getTo());
        trainScheduleListFilter.setDeparture(reservation.getFrom());
        if (!reservation.isReturnWay()) {
            LocalDateTime fromDate = reservation.getOnewayDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
            LocalDateTime toDate = fromDate.toLocalDate().atTime(LocalTime.MAX);
            trainScheduleListFilter.setFromDate(Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()));
            trainScheduleListFilter.setToDate(Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            trainScheduleListFilter.setFromDate(Date.from(reservation.getOnewayDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            trainScheduleListFilter.setToDate(Date.from(reservation.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }

        model.addAttribute("TrainScheduleList", trainScheduleService.filter(trainScheduleListFilter));
        return "reservation";
    }

    @PostMapping(value = "/paymentSummary")
    private String paymentSummary(@Valid ReservationProceed proceed, Model model, BindingResult result) {
        if (!proceed.getTrainSchedule().getTrainDepartureArrivalTimeList().isEmpty()) {
            proceed.setTrainDepartureArrivalTime(proceed.getTrainSchedule().getTrainDepartureArrivalTimeList().get(proceed.getTrainSchedule().getTrainDepartureArrivalTimeList().size() - 1));
        }
        model.addAttribute("reservationProceed", proceed);
        return "paymentSummary";
    }

    @PostMapping(value = "/userInformation")
    private String userInformation(@Valid ReservationProceed proceed, Model model, BindingResult result, @AuthenticationPrincipal UserDetails userDetails) {
        Booking booking = new Booking();
        booking.setPassengers(proceed.getPassengers());
        booking.setPrice(proceed.getTrainSchedule().getTrainSeatPriceAndAvailabilities().stream().filter(e -> e.getTrainClass().equals(proceed.getTrainClass())).findFirst().get().getPrice());
        booking.setActive(true);
        booking.setTrainSchedule(proceed.getTrainSchedule());
        booking.setFrom(proceed.getFrom());
        booking.setTo(proceed.getTo());
        booking.setTrainClass(proceed.getTrainClass());

        if (userDetails == null) {
            booking.setContact(proceed.getContact());
        } else {
            booking.setUser(userService.findUserByEmail(userDetails.getUsername()));
        }
        model.addAttribute("isUser", userDetails != null);

        TrainSeatPriceAndAvailability TSPAA = booking.getTrainSchedule().getTrainSeatPriceAndAvailabilities().stream().filter(e -> e.getTrainClass().equals(booking.getTrainClass())).findAny().get();
        TSPAA.setAvailability(TSPAA.getAvailability() - booking.getPassengers());
        trainScheduleService.saveAvailability(TSPAA);

        booking.setDate(new Date());
        model.addAttribute("booking", bookingService.Save(booking));

        return "userInformation";
    }

    @PostMapping(value = "/ticketInformation")
    private String ticketInformation(@Valid Booking booking, Model model, BindingResult result) {
        booking = bookingService.findById(booking.getId());
        model.addAttribute("booking", new Ticket(booking));
        return "ticketInformation";
    }
}
