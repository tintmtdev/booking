package com.users.booking.service;

import com.users.booking.domain.MeetingRoom;
import com.users.booking.dto.enums.BookingStatusEnum;

public interface MeetingRoomService {
    MeetingRoom getMeetingRoomByIdAndStatus(Integer meetingRoomId, BookingStatusEnum status);
}
