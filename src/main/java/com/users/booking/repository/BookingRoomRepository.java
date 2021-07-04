package com.users.booking.repository;

import com.users.booking.domain.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRoomRepository extends JpaRepository<BookingRoom, Integer> {

    @Query(value = "select b.* from booking_room b where b.meeting_room_id = :meetingRoomId" +
            " and b.status = :status" +
            " and ((:startDate between b.start_date and b.end_Date)" +
            " or (:endDate between b.start_date and b.end_Date)" +
            " or (b.start_date between :startDate and :endDate)" +
            " or (b.end_date between :startDate and :endDate))", nativeQuery = true)
    List<BookingRoom> findBookingRoomOnTime(
            @Param("meetingRoomId") Integer meetingRoomId,
            @Param("status") Integer status,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

}
