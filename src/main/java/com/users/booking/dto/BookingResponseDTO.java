package com.users.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO implements Serializable {
    private Integer id;
    private String employName;
    private String bookingRoom;
    private Date startTime;
    private Date endTime;
}
