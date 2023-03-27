package com.lk.RailwayDepartment.controller;

import com.google.gson.Gson;
import com.lk.RailwayDepartment.Entity.*;
import com.lk.RailwayDepartment.Model.*;
import com.lk.RailwayDepartment.Service.BookingService;
import com.lk.RailwayDepartment.Service.ClassService;
import com.lk.RailwayDepartment.Service.StationService;
import com.lk.RailwayDepartment.Service.TrainScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationRestControllers {
    @Autowired
    private StationService stationService;
    @Autowired
    private TrainScheduleService trainScheduleService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ClassService classService;

    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MMM d, yyyy");

    @GetMapping(value = "/stations")
    private List<StationModel> stations() {
        return stationService.findAllByActive().stream().map(e -> new StationModel(e.getId(), e.getName())).collect(Collectors.toList());
    }

    @PostMapping(value = "/find")
    private String find(@Valid @RequestBody ReservationModel reservationModel) {
        Gson gson = new Gson();
        Station from = null;
        Station to = null;
        Date parse = null;
        int number =0;
        ArrayList<ErrorLog> errorLogs = new ArrayList<>();
        if (reservationModel.getFrom() == null || reservationModel.getFrom().isBlank()) {
            errorLogs.add(new ErrorLog("1010", "Please Select The From"));
        } else {
            try {
                int f = Integer.parseInt(reservationModel.getFrom());
                from = stationService.findById(f);
                if (from == null) {
                    errorLogs.add(new ErrorLog("1010", "Please Select The From valid Station"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Select The From valid Station"));
            }
        }

        if (reservationModel.getTo() == null || reservationModel.getTo().isBlank()) {
            errorLogs.add(new ErrorLog("1011", "Please Select The To or Invalid Station"));
        } else {
            try {
                int t = Integer.parseInt(reservationModel.getTo());
                to = stationService.findById(t);
                if (to == null) {
                    errorLogs.add(new ErrorLog("1010", "Please Select The To valid Station"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Select The To valid Station"));
            }
        }

        if (reservationModel.getDate() == null || reservationModel.getDate().isBlank()) {
            errorLogs.add(new ErrorLog("1012", "Please Enter Date"));
        } else {
            try {
                parse = simpleDateFormat.parse(reservationModel.getDate());
                if(parse.before(new Date())){
                    errorLogs.add(new ErrorLog("1019", "Invalid Date"));
                }
            } catch (ParseException e) {
                errorLogs.add(new ErrorLog("1019", "Invalid Date Format"));
            }
        }

        if (reservationModel.getPassengers() == null || reservationModel.getPassengers().isBlank()) {
            errorLogs.add(new ErrorLog("1013", "Please Enter Passenger Count"));
        }else {
            try {
                number = Integer.parseInt(reservationModel.getPassengers());
                if (number<=0) {
                    errorLogs.add(new ErrorLog("1010", "Please Enter valid Passenger Count"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Enter valid Passenger Count"));
            }
        }


        if (!errorLogs.isEmpty()) {
            return gson.toJson(errorLogs);
        }

        TrainScheduleListFilter trainScheduleListFilter = new TrainScheduleListFilter();
        trainScheduleListFilter.setPassengers(number);
        trainScheduleListFilter.setDeparture(from);
        trainScheduleListFilter.setArrival(to);
        trainScheduleListFilter.setActive(true);

        LocalDateTime fromDate = parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDateTime toDate = fromDate.toLocalDate().atTime(LocalTime.MAX);
        trainScheduleListFilter.setFromDate(Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()));
        trainScheduleListFilter.setToDate(Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant()));


        Station finalTo = to;
        Station finalFrom = from;
        int finalNumber = number;
        Date finalDate=parse;
        List<TrainsModel> collect = trainScheduleService.filter(trainScheduleListFilter).stream().map(e -> new TrainsModel(
                String.valueOf(finalFrom.getId()), String.valueOf(finalTo.getId()), simpleDateFormat.format(finalDate), finalNumber,
                e.getId(), e.getTrain().getName(),
                e.getTrainSeatPriceAndAvailabilities().stream()
                        .map(s -> new SeatAvailableModel(s.getSeats(), s.getAvailability(), s.getPrice(), s.getTrainClass().getId(), s.getTrainClass().getName()))
                        .collect(Collectors.toList()))).collect(Collectors.toList());


        return gson.toJson(collect);
    }

    @PostMapping(value = "/book")
    private String book(@Valid @RequestBody BookingModel bookingModel) {
        Gson gson = new Gson();

        Station from = null;
        Station to = null;
        int number=0;
        TrainSchedule trainSchedule = null;
        TrainClass trainClass=null;

        ArrayList<ErrorLog> errorLogs = new ArrayList<>();

        if (bookingModel.getFrom() == null || bookingModel.getFrom().isBlank()) {
            errorLogs.add(new ErrorLog("1010", "Please Select The From"));
        } else {
            try {
                int f = Integer.parseInt(bookingModel.getFrom());
                from = stationService.findById(f);
                if (from == null) {
                    errorLogs.add(new ErrorLog("1010", "Please Select The From valid Station"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Select The From valid Station"));
            }
        }

        if (bookingModel.getTo() == null || bookingModel.getTo().isBlank()) {
            errorLogs.add(new ErrorLog("1011", "Please Select The To or Invalid Station"));
        } else {
            try {
                int t = Integer.parseInt(bookingModel.getTo());
                to = stationService.findById(t);
                if (to == null) {
                    errorLogs.add(new ErrorLog("1010", "Please Select The To valid Station"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Select The To valid Station"));
            }
        }

        if (bookingModel.getPassengers() == null || bookingModel.getPassengers().isBlank()) {
            errorLogs.add(new ErrorLog("1013", "Please Enter Passenger Count"));
        }else {
            try {
                number = Integer.parseInt(bookingModel.getPassengers());
                if (number<=0) {
                    errorLogs.add(new ErrorLog("1010", "Please Enter valid Passenger Count"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Enter valid Passenger Count"));
            }
        }

        if (bookingModel.getTrainId() == null || bookingModel.getTrainId().isBlank()) {
            errorLogs.add(new ErrorLog("1014", "Please enter Train"));
        }else{
            try {
                int t = Integer.parseInt(bookingModel.getTrainId());
                trainSchedule = trainScheduleService.findById(t);
                if (trainSchedule == null) {
                    errorLogs.add(new ErrorLog("1010", "Please Select The To valid Train"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Select The To valid Train"));
            }
        }

        if (bookingModel.getClassId() == null || bookingModel.getClassId().isBlank()) {
            errorLogs.add(new ErrorLog("1015", "Please enter Train Class"));
        }else{
            try {
                int t = Integer.parseInt(bookingModel.getClassId());
                trainClass = classService.findById(t);
                if (trainClass == null) {
                    errorLogs.add(new ErrorLog("1010", "Please Select The To valid Train Class"));
                }
            } catch (NumberFormatException e) {
                errorLogs.add(new ErrorLog("1010", "Please Select The To valid Train Class"));
            }
        }

        if (bookingModel.getContact() == null || bookingModel.getContact().isBlank()) {
            errorLogs.add(new ErrorLog("1016", "Please enter Passenger Contact number"));
        }

        if (bookingModel.getName() == null || bookingModel.getNumber() == null ||
                bookingModel.getExpire() == null || bookingModel.getCsv() == null) {
            errorLogs.add(new ErrorLog("1020", "Please send Payment Card details"));
        }

        if (!errorLogs.isEmpty()) {
            return gson.toJson(errorLogs);
        }

        Booking booking = new Booking();
        booking.setPassengers(number);
        TrainClass finalTrainClass = trainClass;
        booking.setPrice(trainSchedule.getTrainSeatPriceAndAvailabilities().stream().filter(e -> e.getTrainClass().equals(finalTrainClass)).findFirst().get().getPrice());
        booking.setActive(true);
        booking.setTrainSchedule(trainSchedule);
        booking.setFrom(from);
        booking.setTo(to);
        booking.setTrainClass(trainClass);
        booking.setContact(bookingModel.getContact());
        booking.setDescription(bookingModel.getDescription());

        TrainSeatPriceAndAvailability TSPAA = booking.getTrainSchedule().getTrainSeatPriceAndAvailabilities().stream().filter(e -> e.getTrainClass().equals(booking.getTrainClass())).findAny().get();
        TSPAA.setAvailability(TSPAA.getAvailability() - booking.getPassengers());
        trainScheduleService.saveAvailability(TSPAA);

        booking.setDate(new Date());
        bookingService.Save(booking);

        return gson.toJson(new ErrorLog("1000", "Booking Confirm"));
    }
}
