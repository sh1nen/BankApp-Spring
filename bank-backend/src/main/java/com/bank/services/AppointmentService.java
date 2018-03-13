package com.bank.services;

import com.bank.domain.Appointment;

import java.util.List;

public interface AppointmentService
{
    Appointment createAppointment(Appointment appointment);
    
    List<Appointment> findAll();
    
    Appointment findAppointment(Long id);
    
    void confirmAppointment(Long id);
}
