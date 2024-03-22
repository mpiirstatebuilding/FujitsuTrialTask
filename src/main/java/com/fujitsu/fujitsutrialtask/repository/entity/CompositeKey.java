package com.fujitsu.fujitsutrialtask.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class CompositeKey implements Serializable {
    private String stationName;
    private Timestamp timestamp;
}
