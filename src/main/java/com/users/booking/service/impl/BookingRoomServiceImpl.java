package com.users.booking.service.impl;

import com.users.booking.domain.BookingRoom;
import com.users.booking.dto.enums.BookingStatusEnum;
import com.users.booking.repository.BookingRoomRepository;
import com.users.booking.service.BookingRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookingRoomServiceImpl implements BookingRoomService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BookingRoomRepository bookingRoomRepository;

    @Override
    @Transactional
    public BookingRoom saveBooking(BookingRoom bookingRoom) {
        return bookingRoomRepository.save(bookingRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingRoom> getCurrentBookingRoomAndOnTime(
            Integer meetingRoomId,
            BookingStatusEnum status,
            Date start,
            Date end) {
        return bookingRoomRepository.findBookingRoomOnTime(meetingRoomId, status.ordinal(), start, end);

    }


}
