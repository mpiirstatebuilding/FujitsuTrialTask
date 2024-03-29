package com.fujitsu.fujitsutrialtask.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "WEATHER_DATA")
@Entity
public class WeatherDataEntry {

  @EmbeddedId
  private CompositeKey compositeKey;
  @Column(name = "WMO_CODE")
  private String wmoCode;
  @Column(name = "AIR_TEMP")
  private Double airTemperature;
  @Column(name = "WIND_SPEED")
  private Double windSpeed;
  @Column(name = "PHENOMENON")
  private String phenomenon;
}
