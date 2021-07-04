package com.users.booking.service;

import com.users.booking.domain.BookingRoom;
import com.users.booking.dto.enums.BookingStatusEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface BookingRoomService {
    BookingRoom saveBooking(BookingRoom bookingRoom);

    List<BookingRoom> getCurrentBookingRoomAndOnTime(
            Integer meetingRoomId,
            BookingStatusEnum status,
            Date startDate,
            Date endDate);
}
