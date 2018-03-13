package com.bank.services.impl;

import com.bank.domain.Appointment;
import com.bank.repositories.AppointmentRepository;
import com.bank.services.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService
{
    private AppointmentRepository appointmentRepository;
    
    private AppointmentServiceImpl(AppointmentRepository appointmentRepository)
    {
        this.appointmentRepository = appointmentRepository;
    }
    
    public Appointment createAppointment(Appointment appointment)
    {
       return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> findAll()
    {
        return appointmentRepository.findAll();
    }
    
    public Appointment findAppointment(Long id)
    {
        return appointmentRepository.findOne(id);
    }
    
    public void confirmAppointment(Long id)
    {
        Appointment appointment = findAppointment(id);
        appointment.setConfirmed(true);
        appointmentRepository.save(appointment);
    }
}
