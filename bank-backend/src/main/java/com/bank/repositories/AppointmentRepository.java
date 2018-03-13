package com.bank.repositories;

import com.bank.domain.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>
{
    List<Appointment> findAll();
}
