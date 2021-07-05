package com.users.booking.service;

import com.users.booking.domain.BookingRoom;
import com.users.booking.dto.enums.BookingStatusEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRoomService {
    BookingRoom saveBooking(BookingRoom bookingRoom);

    List<BookingRoom> getCurrentBookingRoomAndOnTime(
            Integer meetingRoomId,
            BookingStatusEnum status,
            Date startDate,
            Date endDate);

    Optional<BookingRoom> getBookingRoomById(Integer bookingRoomId);

    List<BookingRoom> findByBookingRoomByLimit(
            String roomName,
            String employName,
            BookingStatusEnum status,
            Date start,
            Date end);
}
