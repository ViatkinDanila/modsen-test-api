package com.modsen.core.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50, nullable = false)
    private String topic;
    @Column
    private String description;
    @Column(length = 50, nullable = false)
    private String organizer;
    @Column(name = "start_date",nullable = false)
    private Instant startDate;
    @Column(length = 75, nullable = false)
    private String address;

    public Meeting(String topic, String description,
                   String organizer, Instant startDate,
                   String address){
        this.topic = topic;
        this.description = description;
        this.organizer = organizer;
        this.startDate = startDate;
        this.address = address;
    }
}
