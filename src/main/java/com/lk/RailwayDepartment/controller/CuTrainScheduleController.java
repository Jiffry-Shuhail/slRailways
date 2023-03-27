package com.lk.RailwayDepartment.controller;

import com.lk.RailwayDepartment.Entity.*;
import com.lk.RailwayDepartment.Model.TrainScheduleListFilter;
import com.lk.RailwayDepartment.Service.ClassService;
import com.lk.RailwayDepartment.Service.StationService;
import com.lk.RailwayDepartment.Service.TrainScheduleService;
import com.lk.RailwayDepartment.Service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CuTrainScheduleController {

    private final SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private StationService stationService;
    @Autowired
    private ClassService classService;
    @Autowired
    private TrainService trainService;
    @Autowired
    private TrainScheduleService trainScheduleService;

    private static final long ONE_MINUTE_IN_MILLIS = 60000;

    private void Initialize(Model model){
        model.addAttribute("stations", stationService.findAllByActive());
        model.addAttribute("classes", classService.findAllByStatus());
        model.addAttribute("trains", trainService.findAllByActive());
    }

    private void schedule(TrainSchedule trainSchedule){
        Train train = trainSchedule.getTrain();
        if(train!=null){
            trainSchedule.setTrain(train);
            trainSchedule.setTrainSeatPriceAndAvailabilities(
                    train.getTrainHasClasses().stream().map(e->
                                    new TrainSeatPriceAndAvailability(e.getSeats(),e.getSeats(), e.getPrice(),e.getTrainClass()))
                            .collect(Collectors.toList()));

            List<TrainDepartureArrivalTime> tspaa=new ArrayList<>();
            List<StationsDuration> stationsDurations = train.getStationsDurations();
            for(int i=0; i  < stationsDurations.size(); i++){
                StationsDuration stationsDuration = stationsDurations.get(i);
                Date arrival=null;
                Date departure=null;
                if(i==0){
                    arrival=stationsDuration.getDuration();
                    departure = addMinutesToDate(10,arrival);
                }else{
                    arrival=getArrival(stationsDurations.get(i-1).getDuration(), stationsDuration.getDuration());
                    departure = addMinutesToDate(10,arrival);
                }
                tspaa.add(new TrainDepartureArrivalTime(stationsDuration.getStation(), arrival,departure));
            }
            trainSchedule.setTrainDepartureArrivalTimeList(tspaa);
        }
    }

    @GetMapping(value = "/dashboard/cuTrainSchedule/{train}")
    private String cuTrainSchedule(@PathVariable Train train, Model model){
        Initialize(model);
        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setTrain(train);
        schedule(trainSchedule);
        model.addAttribute("trainSchedule", trainSchedule);
        return "cuTrainSchedule";
    }

    @GetMapping(value = "/dashboard/cuTrainSchedule")
    private String cuTrainSchedule(Model model){
        Initialize(model);
        TrainSchedule trainSchedule = new TrainSchedule();
        schedule(trainSchedule);
        model.addAttribute("trainSchedule", trainSchedule);
        return "cuTrainSchedule";
    }

    @PostMapping(value = {"/dashboard/cuTrainSchedule","/dashboard/cuTrainSchedule/{train}"})
    private String cuTrainSchedule(@Valid TrainSchedule trainSchedule, Model model, BindingResult result){
        Initialize(model);
        if(result.hasErrors()){
            model.addAttribute("trainSchedule", trainSchedule);
            return "cuTrainSchedule";
        }
        trainScheduleService.save(trainSchedule);
        TrainSchedule ts = new TrainSchedule();
        schedule(ts);
        model.addAttribute("trainSchedule", ts);
        return "cuTrainSchedule";
    }

    @GetMapping(value = "/dashboard/editTrainSchedule/{trainSchedule}")
    private String editTrainSchedule(@Valid TrainSchedule trainSchedule, Model model){
        Initialize(model);
        model.addAttribute("trainSchedule", trainSchedule);
        return "cuTrainSchedule";
    }

    @PostMapping(value = {"/dashboard/editTrainSchedule","/dashboard/editTrainSchedule/{trainSchedule}"})
    private String editTrainSchedule(@Valid TrainSchedule trainSchedule, Model model, BindingResult result){
        Initialize(model);
        if(result.hasErrors()){
            model.addAttribute("trainSchedule", trainSchedule);
            return "cuTrainSchedule";
        }
        trainScheduleService.update(trainSchedule);
        TrainSchedule ts = new TrainSchedule();
        schedule(ts);
        model.addAttribute("trainSchedule", ts);
        return "cuTrainSchedule";
    }

    @GetMapping(value = {"/dashboard/deactivateTrainSchedule/{trainSchedule}", "/dashboard/activateTrainSchedule/{trainSchedule}"})
    private String activateOrDeactivateTrainSchedule(@Valid TrainSchedule trainSchedule, Model model){
        trainScheduleService.activateOrDeactivate(trainSchedule);
        Initialize(model);
        InitializeScheduleList(model);
        return "trainScheduleList";
    }

    private Date addMinutesToDate(int minutes, Date beforeTime) {
        return new Date(beforeTime.getTime()+ (minutes * ONE_MINUTE_IN_MILLIS));
    }

    private Date getArrival(Date prev, Date beforeTime) {
        return new Date(beforeTime.getTime()+ (
                (prev.getMinutes() * ONE_MINUTE_IN_MILLIS)
        +(prev.getHours()*60*ONE_MINUTE_IN_MILLIS)
        + ((prev.getSeconds()/60)*ONE_MINUTE_IN_MILLIS)));
    }

    private void InitializeScheduleList(Model model){
        TrainScheduleListFilter trainScheduleListFilter = new TrainScheduleListFilter();
        LocalDateTime fromDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDateTime toDate = fromDate.toLocalDate().atTime(LocalTime.MAX);
        trainScheduleListFilter.setFromDate(Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()));
        trainScheduleListFilter.setToDate(Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant()));
        trainScheduleListFilter.setActive(true);
        model.addAttribute("TrainScheduleListFilter", trainScheduleListFilter);
        model.addAttribute("TrainScheduleList", trainScheduleService.filter(trainScheduleListFilter));
    }

    @GetMapping(value = "/dashboard/trainScheduleList")
    private String trainScheduleList(Model model){
        Initialize(model);
        InitializeScheduleList(model);
        return "trainScheduleList";
    }

    @PostMapping(value = "/dashboard/trainScheduleList")
    private String trainScheduleList(@Valid TrainScheduleListFilter trainScheduleListFilter, Model model, BindingResult result){
        Initialize(model);
        model.addAttribute("TrainScheduleListFilter", trainScheduleListFilter);
        model.addAttribute("TrainScheduleList", trainScheduleService.filter(trainScheduleListFilter));
        return "trainScheduleList";
    }
}
