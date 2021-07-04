package com.users.booking.rest;


import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.ResponseData;
import com.users.booking.facade.BookingRoomFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingRoomFacade bookingRoomFacade;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseData> updateBookingRoomByEmployee(
            @RequestBody BookingRequestDTO bookingRequestDTO) {
        ResponseData responseData = bookingRoomFacade.updateBookingRoomByEmployee(bookingRequestDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }



}
