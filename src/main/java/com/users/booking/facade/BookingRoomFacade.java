package com.users.booking.facade;

import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.ResponseData;
import org.springframework.stereotype.Component;

@Component
public interface BookingRoomFacade {
    ResponseData updateBookingRoomByEmployee(BookingRequestDTO bookingRequestDTO);
}
