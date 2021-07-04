package com.users.booking.domain;

import com.users.booking.dto.enums.BookingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class MeetingRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_room_generator")
    @SequenceGenerator(
            name = "meeting_room_generator",
            sequenceName = "meeting_room_id_seq",
            allocationSize = 1
    )
    private Integer id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatusEnum status;

}
