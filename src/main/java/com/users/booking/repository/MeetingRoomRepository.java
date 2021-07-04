package com.users.booking.repository;

import com.users.booking.domain.MeetingRoom;
import com.users.booking.dto.enums.BookingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
    Optional<MeetingRoom> findByIdAndStatus(Integer id, BookingStatusEnum status);
}
