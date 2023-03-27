package com.lk.RailwayDepartment.controller;

import com.lk.RailwayDepartment.Entity.User;
import com.lk.RailwayDepartment.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/login"})
    public String login(Model model){
        return "login";
    }

    @GetMapping(value = {"/signup"})
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registration(@Valid User user, BindingResult result, Model model){
        if(user.getNic().isEmpty() && user.getPassword().isEmpty()){
            result.rejectValue("nic", null,
                    "Please enter NIC or Passport Number");
            result.rejectValue("passport", null,
                    "Please enter NIC or Passport Number");
        }

        User existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/signup";
        }

        user.setDate(new Date());
        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/login";
    }

}
