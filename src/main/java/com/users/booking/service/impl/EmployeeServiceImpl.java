package com.users.booking.service.impl;

import com.users.booking.domain.Employee;
import com.users.booking.dto.enums.BookingStatusEnum;
import com.users.booking.repository.EmployeeRepository;
import com.users.booking.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeByIdAndStatus(Integer id, BookingStatusEnum status) {
        return employeeRepository.findByIdAndStatus(id, status).orElse(null);
    }
}
