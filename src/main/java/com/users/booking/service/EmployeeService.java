package com.users.booking.service;

import com.users.booking.domain.Employee;
import com.users.booking.dto.enums.BookingStatusEnum;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeService {
    Employee findEmployeeByIdAndStatus(Integer id, BookingStatusEnum status);
}
