package com.lk.RailwayDepartment.controller;

import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Model.TrainListFilter;
import com.lk.RailwayDepartment.Service.ClassService;
import com.lk.RailwayDepartment.Service.StationService;
import com.lk.RailwayDepartment.Service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CuTrainController {

    @Autowired
    private StationService stationService;
    @Autowired
    private ClassService classService;
    @Autowired
    private TrainService trainService;

    private void Initialize(Model model){
        model.addAttribute("stations", stationService.findAllByActive());
        model.addAttribute("classes", classService.findAllByStatus());
    }

    public void InitializeList(Model model){
        TrainListFilter trainListFilter = new TrainListFilter();
        trainListFilter.setActive(true);
        model.addAttribute("trainListFilter", trainListFilter);
        model.addAttribute("trains", trainService.findAllByActive());
    }

    @GetMapping(value = {"/dashboard/cuTrain"})
    public String cuTrain(Model model){
        Initialize(model);
        model.addAttribute("train", new Train());
        return "cuTrain";
    }

    @PostMapping(value = "/dashboard/cuTrain")
    public String cuTrain(@Valid Train train, BindingResult result, Model model){
        Initialize(model);

        Train byName = trainService.findByName(train.getName());
        if(byName!=null){
            result.rejectValue("name",null,"Please enter Valid Name, This Name Already Exist");
        }

        if(result.hasErrors()){
            model.addAttribute("train", train);
            return "cuTrain";
        }

        trainService.save(train);
        model.addAttribute("train", new Train());
        return "cuTrain";
    }

    @GetMapping(value = {"/dashboard/trainList"})
    public String trainList(Model model){
        Initialize(model);
        InitializeList(model);
        return "trainList";
    }

    @PostMapping(value = {"/dashboard/trainList"})
    public String cuTrain(@Valid TrainListFilter trainListFilter, BindingResult result, Model model){
        Initialize(model);
        model.addAttribute("trainListFilter",trainListFilter);
        model.addAttribute("trains", trainService.filter(trainListFilter));
        return "trainList";
    }

    @GetMapping(value = "/dashboard/editTrain/{train}")
    public String editTrain(@PathVariable Train train,Model model){
        Initialize(model);
        model.addAttribute("train", train);
        return "cuTrain";
    }

    @PostMapping(value = "/dashboard/editTrain/{train}")
    public String editTrain(@Valid Train train, BindingResult result, Model model){
        Initialize(model);

        if(result.hasErrors()){
            model.addAttribute("train", train);
            return "cuTrain";
        }

        trainService.update(train);
        InitializeList(model);
        return "trainList";
    }

    @GetMapping(value = "/dashboard/deactivateTrain/{train}")
    public String deactivateTrain(@PathVariable Train train,Model model){
        Initialize(model);
        trainService.deactivate(train);
        InitializeList(model);
        return "trainList";
    }

    @GetMapping(value = "/dashboard/activateTrain/{train}")
    public String activateTrain(@PathVariable Train train,Model model){
        Initialize(model);
        trainService.activate(train);
        InitializeList(model);
        return "trainList";
    }
}
