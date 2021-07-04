package com.users.booking.repository;

import com.users.booking.domain.Employee;
import com.users.booking.dto.enums.BookingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByIdAndStatus(Integer id, BookingStatusEnum status);
}
