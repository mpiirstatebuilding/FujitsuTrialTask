package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.repository.WeatherDataEntryRepository;
import com.fujitsu.fujitsutrialtask.repository.entity.CompositeKey;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.DeliveryFeeException;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.WeatherConditionException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryParameterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryFeeService {

  final WeatherDataEntryRepository repository;
  @Value("#{${delivery-fee.query.city-to-station}}")
  HashMap<String, String> stationDictionary;
  @Value("#{${delivery-fee.rbf.city}}")
  HashMap<String, Float> cityRbf;
  @Value("#{${delivery-fee.rbf.vehicle}}")
  HashMap<String, Float> vehicleRbf;
  @Value("#{'${delivery-fee.atef}'.split(',')}")
  List<Float> atef;
  @Value("#{'${delivery-fee.wsef}'.split(',')}")
  List<Float> wsef;
  @Value("#{${delivery-fee.wpef}}")
  HashMap<String, Float> wpef;

  /**
   * Calculate delivery fee.
   *
   * @param city            - determines base fee
   * @param vehicle         - subtracts from city base fee
   * @param timestampString - used to get respective weather data entry, if not present or entry
   *                        with respective timestamp not found, gets latest entry for city
   * @return delivery fee as float
   */
  public Float getDeliveryFee(String city, String vehicle, String timestampString)
      throws DeliveryFeeException, WeatherConditionException {
    Float deliveryFee = 0.0f;

    city = city.toLowerCase();
    vehicle = vehicle.toLowerCase();

    // get city fee
    if (!cityRbf.containsKey(city)) {
      throw new QueryParameterException("Unknown city!");
    }
    deliveryFee += cityRbf.get(city);

    // subtract by vehicle
    if (!vehicleRbf.containsKey(vehicle)) {
      throw new QueryParameterException("Unknown vehicle!");
    }
    deliveryFee -= vehicleRbf.get(vehicle);

    // extra fee rules are only outlined for scooters and bikes, so if the vehicle type is car there's no need to do anymore
    if (!vehicle.equals("car")) {
      deliveryFee = addExtraFees(city, vehicle, timestampString, deliveryFee);
    }

    log.info("Successfully calculated delivery fee.");
    return deliveryFee;
  }

  private Float addExtraFees(String city, String vehicle, String timestampString, Float deliveryFee)
      throws DeliveryFeeException, WeatherConditionException {
    Optional<WeatherDataEntry> entryOptional = Optional.empty();
    String station = stationDictionary.get(city);
    if (timestampString != null) {
      long timestampLong = Long.parseLong(timestampString) * 1000;
      Timestamp timestamp = new Timestamp(timestampLong);
      entryOptional = repository.findById(new CompositeKey(station, timestamp));
    }

    if (entryOptional.isEmpty()) { // it would be empty if either the timestamp was not provided or the last clause was unable to find a suitable entry
      entryOptional = repository.findLatestEntryByStation(station);
      if (entryOptional.isEmpty()) {
        throw new DeliveryFeeException("Could not find relevant weather data!");
      }
    }

    WeatherDataEntry entry = entryOptional.get();

    // extra fees based on air temperature
    deliveryFee = getAtef(deliveryFee, entry);

    deliveryFee = getWpef(deliveryFee, entry);

    // extra fees based on wind speed
    if (vehicle.equals("bike")) {
      deliveryFee = getWsef(deliveryFee, entry);
    }
    return deliveryFee;
  }

  private Float getWsef(Float deliveryFee, WeatherDataEntry entry)
      throws WeatherConditionException {
    Double windSpeed = entry.getWindSpeed();
    if (windSpeed > 20.0) {
      throw new WeatherConditionException("Usage of selected vehicle type is forbidden!");
    } else if (windSpeed >= 10) {
      deliveryFee += wsef.get(0);
    }
    return deliveryFee;
  }

  private Float getWpef(Float deliveryFee, WeatherDataEntry entry)
      throws WeatherConditionException {
    String phenomenon = entry.getPhenomenon().toLowerCase();
    if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
      deliveryFee += wpef.get("snow");
    } else if (phenomenon.contains("rain") || phenomenon.contains(
        "shower")) { // if it was a snow shower, it would have been dealt with in the last clause
      deliveryFee += wpef.get("rain");
    } else if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains(
        "thunder")) {
      throw new WeatherConditionException("Usage of selected vehicle type is forbidden!");
    }
    return deliveryFee;
  }

  private Float getAtef(Float deliveryFee, WeatherDataEntry entry) {
    Double airTemperature = entry.getAirTemperature();
    if (airTemperature < -10.0) {
      deliveryFee += atef.get(0);
    } else if (airTemperature <= 0.0) {
      deliveryFee += atef.get(1);
    }
    return deliveryFee;
  }
}
