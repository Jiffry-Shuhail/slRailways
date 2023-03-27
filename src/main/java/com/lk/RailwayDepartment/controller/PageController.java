package com.lk.RailwayDepartment.controller;
import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Model.Reservation;
import com.lk.RailwayDepartment.Service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class PageController {

    @Autowired
    private StationService stationService;

    public static final String[][] SERVICES = {
            {"Colombo Fort - Beliatta", "Intercity & Express trains", "1st ,2nd and 3rd", ""},
            {"Colombo Fort - Badulla", "Intercity & Express trains", "1st ,2nd, 3rd and observations saloon", ""},
            {"Colombo Fort - Talaimannar", "Night mail train", "2nd class", ""},
            {"Colombo Fort - Jaffna", "Intercity, Express & Night mail trains", "1st ,2nd and 3rd", "grey"},
            {"Colombo Fort - Trincomalee", "Night mail train", "2nd and 3rd", "grey"},
            {"Kandy - Badulla", "Slow train", "Observations saloon", "grey"},
            {"Colombo Fort - Kandy", "Intercity & Express trains", "1st ,2nd, 3rd and observations saloon","black"},
            {"Colombo Fort - Batticaloa", "Intercity, Express & Night mail trains", "1st, 2nd and 3rd","black"}};

    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        model.addAttribute("services", SERVICES);
        model.addAttribute("stations", stationService.findAllByActive());
        Reservation reservation=new Reservation();
        reservation.setHome(true);
        reservation.setPassengers(1);
        model.addAttribute("reservation", reservation);
        return "index";
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard(Model model){
        return "dashboard";
    }

    @GetMapping(value = {"/api"})
    public String api(Model model){
        return "api";
    }



}
