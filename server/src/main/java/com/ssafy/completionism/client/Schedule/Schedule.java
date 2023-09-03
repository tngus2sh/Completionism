package com.ssafy.completionism.client.Schedule;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    Long id;
}
