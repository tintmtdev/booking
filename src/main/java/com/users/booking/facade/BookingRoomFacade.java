package com.users.booking.facade;

import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.ResponseData;
import com.users.booking.dto.SearchParamDTO;
import org.springframework.stereotype.Component;

@Component
public interface BookingRoomFacade {
    ResponseData updateBookingRoomByEmployee(BookingRequestDTO bookingRequestDTO);

    ResponseData deleteBookingRoom(Integer bookingRoomId);

    ResponseData searchBookingRoom(SearchParamDTO searchParam);
}
