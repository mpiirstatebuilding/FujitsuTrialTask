package com.fujitsu.fujitsutrialtask.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class CompositeKey implements Serializable {
    private String stationName;
    private Timestamp timestamp;
}
