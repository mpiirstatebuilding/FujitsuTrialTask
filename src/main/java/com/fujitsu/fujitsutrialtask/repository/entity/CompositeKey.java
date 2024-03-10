package com.fujitsu.fujitsutrialtask.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Embeddable
public class CompositeKey implements Serializable {
    private String stationName;
    private Timestamp timestamp;
}
