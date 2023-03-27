package com.lk.RailwayDepartment.controller;

import com.lk.RailwayDepartment.Model.BookingListFilter;
import com.lk.RailwayDepartment.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;

@Controller
public class BookingController {

    @Autowired
    private TrainService trainService;
    @Autowired
    private ClassService classService;
    @Autowired
    private StationService stationService;
    @Autowired
    private BookingService bookingService;

    private void Initialize(Model model){
        model.addAttribute("stations", stationService.findAllByActive());
        model.addAttribute("classes", classService.findAllByStatus());
        model.addAttribute("trains", trainService.findAllByActive());
    }

    @GetMapping(value = "/dashboard/bookingList")
    private String bookingList(Model model){
        Initialize(model);
        BookingListFilter bookingListFilter = new BookingListFilter(true);
        bookingListFilter.setFromDate(UserServiceImpl.FORMATTER.format(new Date()));
        bookingListFilter.setToDate(UserServiceImpl.FORMATTER.format(new Date()));

        model.addAttribute("bookingListFilter", bookingListFilter);
        model.addAttribute("BookingList", bookingService.filter(bookingListFilter));
        return "bookingList";
    }

    @PostMapping(value = "/dashboard/bookingList")
    private String bookingList(@Valid BookingListFilter bookingListFilter, Model model){
        Initialize(model);
        model.addAttribute("bookingListFilter", bookingListFilter);
        model.addAttribute("BookingList", bookingService.filter(bookingListFilter));
        return "bookingList";
    }

}
