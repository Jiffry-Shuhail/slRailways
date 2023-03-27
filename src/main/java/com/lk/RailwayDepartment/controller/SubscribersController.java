package com.lk.RailwayDepartment.controller;

import com.lk.RailwayDepartment.Model.SubscribersListFilter;
import com.lk.RailwayDepartment.Service.RoleService;
import com.lk.RailwayDepartment.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SubscribersController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/dashboard/subscribersList")
    private String subscribersList(Model model){
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("SubscribersList", userService.findAllActiveUsers());
        model.addAttribute("SubscribersListFilter", new SubscribersListFilter(true));
        return "subscribers";
    }

    @PostMapping(value = "/dashboard/subscribersList")
    private String subscribersList(@Valid SubscribersListFilter subscribersListFilter, Model model, BindingResult result){
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("SubscribersListFilter", subscribersListFilter);
        model.addAttribute("SubscribersList", userService.filter(subscribersListFilter));
        return "subscribers";
    }
}
