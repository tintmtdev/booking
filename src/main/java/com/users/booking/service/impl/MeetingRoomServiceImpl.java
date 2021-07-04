package com.users.booking.service.impl;

import com.users.booking.domain.MeetingRoom;
import com.users.booking.dto.enums.BookingStatusEnum;
import com.users.booking.repository.MeetingRoomRepository;
import com.users.booking.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Override
    @Transactional(readOnly = true)
    public MeetingRoom getMeetingRoomByIdAndStatus(Integer meetingRoomId, BookingStatusEnum status) {
        return meetingRoomRepository.findByIdAndStatus(meetingRoomId, status).orElse(null);
    }
}
