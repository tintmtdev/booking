package com.users.booking.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.booking.BookingApplication;
import com.users.booking.dto.BookingRequestDTO;
import com.users.booking.dto.ResponseData;
import com.users.booking.facade.BookingRoomFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {BookingApplication.class})
public class BookingControllerTests {

    ObjectMapper objMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookingRoomFacade bookingRoomFacade;

    String BookingServiceTest = "/bookings";

    @Before
    public void setUp() {

    }

    MockHttpServletRequestBuilder post(String urlTemplate, Object... uriVars){
        return MockMvcRequestBuilders.post(BookingServiceTest + urlTemplate, uriVars);
    }

    @Test
    public void updateBookingRoomByEmployeeTest() throws Exception {
        when(bookingRoomFacade.updateBookingRoomByEmployee(any(BookingRequestDTO.class))).thenReturn(ResponseData.builder().build());
        MockHttpServletRequestBuilder request = post("/")
                .content(objMapper.writeValueAsString(BookingRequestDTO.builder()
                       .employeeId(1)
                        .meetingRoomId(1)
                        .startTime(new Date())
                        .endTime(new Date())
                        .build()))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk());
    }
}
