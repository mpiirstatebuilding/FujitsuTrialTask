package com.fujitsu.fujitsutrialtask.repository.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class CompositeKey implements Serializable {

  private String stationName;
  private Timestamp timestamp;
}
