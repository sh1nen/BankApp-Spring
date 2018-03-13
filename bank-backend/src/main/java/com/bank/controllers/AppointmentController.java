package com.bank.controllers;

import com.bank.domain.Appointment;
import com.bank.domain.User;
import com.bank.services.AppointmentService;
import com.bank.services.UserService;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/appointment")
public class AppointmentController
{
    
    private AppointmentService appointmentService;
    private UserService userService;
    
    private AppointmentController(AppointmentService appointmentService, UserService userService)
    {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }
    
    @ModelAttribute("allCities")
    public List<String> allCities()
    {
        return Arrays.asList("Boston, New York, Chicago, San Francisco".split(","));
    }
    
    @GetMapping("/create")
    public String createAppointment(Model model)
    {
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");
        model.addAttribute("timeString", "");
        
        return "appointment/appointment";
    }
    
    @PostMapping("/create")
    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment,
        @ModelAttribute("dateString") String date,
        @ModelAttribute("timeString") String time,
        Model model,
        Principal principal) throws ParseException
    {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String dateTime = date + " " + time;
        
        Date date1 = format1.parse(dateTime);
        
        appointment.setDate(date1);
        User user = userService.findByUsername(principal.getName()).get();
        appointment.setUser(user);
        
        appointmentService.createAppointment(appointment);
        
        return "redirect:/userFront";
    }
}
