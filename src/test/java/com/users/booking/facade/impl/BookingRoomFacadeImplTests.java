package com.users.booking.facade.impl;

import com.users.booking.domain.BookingRoom;
import com.users.booking.domain.Employee;
import com.users.booking.domain.MeetingRoom;
import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.BookingResponseDTO;
import com.users.booking.dto.ResponseData;
import com.users.booking.dto.enums.BookingStatusEnum;
import com.users.booking.service.BookingRoomService;
import com.users.booking.service.EmployeeService;
import com.users.booking.service.MeetingRoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingRoomFacadeImplTests {

    @InjectMocks
    @Spy
    BookingRoomFacadeImpl bookingRoomFacadeImpl;

    @Mock
    EmployeeService employeeService;

    @Mock
    MeetingRoomService meetingRoomService;

    @Mock
    BookingRoomService bookingRoomService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private BookingRequestDTO initRequestDTO() {
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setEmployeeId(1);
        dto.setMeetingRoomId(1);
        dto.setStartTime(new Date());
        dto.setEndTime(new Date());
        return dto;
    }

    @Test
    public void testUpdateBookingRoomByRangeTimeShouldInvalid() {
        BookingRequestDTO dto = initRequestDTO();
        dto.setStartTime(null);
        ResponseData response;

        response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        assertEquals(response.getMessage(), "Start Date or End Date was null. Please check again.");
        assertEquals(response.getStatus().intValue(), HttpStatus.NOT_FOUND.value());

        dto.setEndTime(null);

        response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        assertEquals(response.getMessage(), "Start Date or End Date was null. Please check again.");
        assertEquals(response.getStatus().intValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateBookingRoomByEmployeeNotFoundShouldInvalid() {
        BookingRequestDTO dto = initRequestDTO();

        when(employeeService.findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(null);

        ResponseData response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        verify(meetingRoomService, never()).getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));
        verify(bookingRoomService, never()).getCurrentBookingRoomAndOnTime(any(Integer.class),
                any(BookingStatusEnum.class), any(Date.class), any(Date.class));

        assertEquals(response.getMessage(), "Employee not exist or inactive.");
        assertEquals(response.getStatus().intValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateBookingRoomByMeetingNotFoundShouldInvalid() {
        BookingRequestDTO dto = initRequestDTO();
        Employee employee = new Employee();
        employee.setId(1);

        when(employeeService.findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(employee);
        when(meetingRoomService.getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(null);

        ResponseData response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        verify(employeeService, times(1)).findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));
        verify(bookingRoomService, never()).getCurrentBookingRoomAndOnTime(any(Integer.class),
                any(BookingStatusEnum.class), any(Date.class), any(Date.class));

        assertEquals(response.getMessage(), "Meeting not exist or inactive.");
        assertEquals(response.getStatus().intValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateBookingRoomByBookingOnTimShouldInvalid() {
        BookingRequestDTO dto = initRequestDTO();
        Employee employee = new Employee();
        employee.setId(1);
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(1);
        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setId(1);
        bookingRoom.setMeetingRoom(meetingRoom);
        bookingRoom.setEmployee(employee);
        List<BookingRoom> bookingRooms = new ArrayList<>();
        bookingRooms.add(bookingRoom);

        when(employeeService.findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(employee);
        when(meetingRoomService.getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(meetingRoom);
        when(bookingRoomService.getCurrentBookingRoomAndOnTime(any(Integer.class),
                any(BookingStatusEnum.class), any(Date.class), any(Date.class))).thenReturn(bookingRooms);

        ResponseData response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        verify(employeeService, times(1)).findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));
        verify(meetingRoomService, times(1)).getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));
        verify(bookingRoomService, never()).saveBooking(any(BookingRoom.class));

        assertEquals(response.getMessage(), "Booking room not available in this time.");
        assertEquals(response.getStatus().intValue(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateBookingRoomBySaveBookingFailShouldInvalid() {
        BookingRequestDTO dto = initRequestDTO();
        Employee employee = new Employee();
        employee.setId(1);
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(1);

        when(employeeService.findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(employee);
        when(meetingRoomService.getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(meetingRoom);
        when(bookingRoomService.getCurrentBookingRoomAndOnTime(any(Integer.class),
                any(BookingStatusEnum.class), any(Date.class), any(Date.class))).thenReturn(null);
        when(bookingRoomService.saveBooking(any(BookingRoom.class))).thenReturn(null);

        ResponseData response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        verify(employeeService, times(1)).findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));
        verify(meetingRoomService, times(1)).getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));

        assertEquals(response.getMessage(), "Internal Server Error.");
        assertEquals(response.getStatus().intValue(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void testUpdateBookingRoomByBookingShouldSuccess() {
        BookingRequestDTO dto = initRequestDTO();
        Employee employee = new Employee();
        employee.setId(1);
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(1);
        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setId(1);
        bookingRoom.setMeetingRoom(meetingRoom);
        bookingRoom.setEmployee(employee);

        when(employeeService.findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(employee);
        when(meetingRoomService.getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class))).thenReturn(meetingRoom);
        when(bookingRoomService.getCurrentBookingRoomAndOnTime(any(Integer.class),
                any(BookingStatusEnum.class), any(Date.class), any(Date.class))).thenReturn(null);
        when(bookingRoomService.saveBooking(any(BookingRoom.class))).thenReturn(bookingRoom);

        ResponseData response = bookingRoomFacadeImpl.updateBookingRoomByEmployee(dto);

        verify(employeeService, times(1)).findEmployeeByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));
        verify(meetingRoomService, times(1)).getMeetingRoomByIdAndStatus(any(Integer.class), any(BookingStatusEnum.class));

        assertEquals(response.getMessage(), "Booking update success.");
        assertEquals(response.getStatus().intValue(), HttpStatus.OK.value());
    }
}
