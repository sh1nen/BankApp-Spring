package com.bank.resources;

import com.bank.domain.Appointment;
import com.bank.services.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@PreAuthorize("hasRole('ADMIN')")
public class AppointmentResource
{
    AppointmentService appointmentService;
    
    public AppointmentResource(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }
    
    @RequestMapping("/all")
    public List<Appointment> findAppointmentList()
    {
        return appointmentService.findAll();
    }
    
    @RequestMapping("/{id}/confirm")
    public void confirmAppointment(@PathVariable("id") Long id)
    {
        appointmentService.confirmAppointment(id);
    }
}
