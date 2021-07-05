package com.users.booking.facade.impl;

import com.users.booking.domain.BookingRoom;
import com.users.booking.domain.Employee;
import com.users.booking.domain.MeetingRoom;
import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.BookingResponseDTO;
import com.users.booking.dto.ResponseData;
import com.users.booking.dto.SearchParamDTO;
import com.users.booking.dto.enums.BookingStatusEnum;
import com.users.booking.facade.BookingRoomFacade;
import com.users.booking.service.BookingRoomService;
import com.users.booking.service.EmployeeService;
import com.users.booking.service.MeetingRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookingRoomFacadeImpl implements BookingRoomFacade {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MeetingRoomService meetingRoomService;

    @Autowired
    BookingRoomService bookingRoomService;

    @Override
    public ResponseData updateBookingRoomByEmployee(BookingRequestDTO bookingRequestDTO) {

        final ResponseData response = new ResponseData();
        try {
            checkCurrentDate(bookingRequestDTO);
            Employee employee = checkEmployee(bookingRequestDTO.getEmployeeId());
            MeetingRoom meetingRoom = checkMeetingRoom(bookingRequestDTO.getMeetingRoomId());
            checkBookingRoomOnTime(bookingRequestDTO, response);

            BookingRoom bookingRoom = BookingRoom.builder()
                    .employee(employee)
                    .meetingRoom(meetingRoom)
                    .startDate(bookingRequestDTO.getStartTime())
                    .endDate(bookingRequestDTO.getEndTime())
                    .status(BookingStatusEnum.ACTIVE)
                    .build();
            BookingRoom dbBookingRoom = bookingRoomService.saveBooking(bookingRoom);
            if (dbBookingRoom != null) {
                response.setMessage("Booking update success.");
                response.setStatus(HttpStatus.OK.value());
                return response;
            }

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return response;
        }
        response.setMessage("Internal Server Error.");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return response;

    }

    private void checkCurrentDate(BookingRequestDTO bookingRequestDTO) throws Exception {
        if (bookingRequestDTO.getStartTime() == null || bookingRequestDTO.getEndTime() == null) {
            logger.error(
                    "referenceDate is null. Cannot process checking and update booking room");
            throw new Exception("Start Date or End Date was null. Please check again.");
        }
    }

    private Employee checkEmployee(Integer employeeId) throws Exception {
        Employee employee = employeeService.findEmployeeByIdAndStatus(
                employeeId,
                BookingStatusEnum.ACTIVE);
        if (employee == null) {
            logger.error(
                    "Employee not exist or inactive. Cannot process checking and update booking room");
            throw new Exception("Employee not exist or inactive.");
        }
        return employee;
    }

    private MeetingRoom checkMeetingRoom(Integer meetingRoomId) throws Exception {
        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomByIdAndStatus(
                meetingRoomId,
                BookingStatusEnum.ACTIVE);
        if (meetingRoom == null) {
            logger.error(
                    "Meeting room not exist or inactive. Cannot process checking and update booking room");
            throw new Exception("Meeting not exist or inactive.");
        }
        return meetingRoom;
    }

    private void checkBookingRoomOnTime(BookingRequestDTO bookingRequestDTO, ResponseData response) throws Exception {
        List<BookingRoom> bookingRooms = bookingRoomService.getCurrentBookingRoomAndOnTime(
                bookingRequestDTO.getMeetingRoomId(),
                BookingStatusEnum.ACTIVE,
                bookingRequestDTO.getStartTime(),
                bookingRequestDTO.getEndTime());
        if (!CollectionUtils.isEmpty(bookingRooms)) {
            List<BookingResponseDTO> responseList = new ArrayList<>();
            bookingRooms.forEach(bookingRoom -> {
                final BookingResponseDTO dto = toBookingRoomDTO(bookingRoom);
                responseList.add(dto);
            });
            response.setData(responseList);
            logger.error(
                    "Booking room not available in this time. Cannot booking now");
           throw new Exception("Booking room not available in this time.");
        }
    }

    @Override
    public ResponseData deleteBookingRoom(Integer bookingRoomId) {
        ResponseData response = new ResponseData();
        try {
            if (bookingRoomId > 0) {
                Optional<BookingRoom> optional = bookingRoomService.getBookingRoomById(bookingRoomId);
                if (optional.isPresent()) {
                    BookingRoom bookingRoom = optional.get();
                    bookingRoom.setStatus(BookingStatusEnum.DELETE);
                    BookingRoom dbBooking = bookingRoomService.saveBooking(bookingRoom);
                    if (dbBooking != null) {
                        response.setMessage("Booking Room was delete success.");
                        response.setStatus(HttpStatus.OK.value());
                        return response;
                    }
                }
            }
            response.setMessage("Booking Room was not exist or fail. Please try again");
            response.setStatus(HttpStatus.NOT_FOUND.value());

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public ResponseData searchBookingRoom(SearchParamDTO searchParam) {
        List<BookingRoom> bookingRooms = bookingRoomService.findByBookingRoomByLimit(
                searchParam.getBookingRoom(),
                searchParam.getEmployName(),
                BookingStatusEnum.ACTIVE,
                searchParam.getStartTime(),
                searchParam.getEndTime());
        List<BookingResponseDTO> responseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bookingRooms)) {
            bookingRooms.forEach(bookingRoom -> {
                final BookingResponseDTO dto = toBookingRoomDTO(bookingRoom);
                responseList.add(dto);
            });
        }
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .message("Response Search Success.")
                .data(responseList)
                .build();
    }

    private BookingResponseDTO toBookingRoomDTO(BookingRoom bookingRoom) {
        final BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(bookingRoom.getId());
        dto.setEmployName(bookingRoom.getEmployee().getFirstName());
        dto.setBookingRoom(bookingRoom.getMeetingRoom().getName());
        dto.setStartTime(bookingRoom.getStartDate());
        dto.setEndTime(bookingRoom.getEndDate());
        return dto;
    }

}
