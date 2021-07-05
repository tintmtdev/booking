package com.users.booking.rest;


import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.ResponseData;
import com.users.booking.dto.SearchParamDTO;
import com.users.booking.facade.BookingRoomFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingRoomFacade bookingRoomFacade;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ResponseData> updateBookingRoomByEmployee(
            @RequestBody BookingRequestDTO bookingRequestDTO) {
        ResponseData responseData = bookingRoomFacade.updateBookingRoomByEmployee(bookingRequestDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookingRoomId}")
    public ResponseEntity<ResponseData> deleteBookingRoomById(
            @PathVariable("bookingRoomId") Integer bookingRoomId) {
        ResponseData responseData = bookingRoomFacade.deleteBookingRoom(bookingRoomId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<ResponseData> deleteBookingRoomById(
            @RequestBody SearchParamDTO searchParamDTO) {
        ResponseData responseData = bookingRoomFacade.searchBookingRoom(searchParamDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }



}
