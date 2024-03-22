package com.fujitsu.fujitsutrialtask.service.mapper;

import com.fujitsu.fujitsutrialtask.repository.entity.CompositeKey;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ParsingException;
import java.sql.Timestamp;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

@Component
public class WeatherDataEntryMapper {

  /**
   * Method used by WeatherDataService to map a DOM Element to a WeatherDataEntry object.
   *
   * @param stationElement - weather station info as DOM Element
   * @param stationName    - name of weather station
   * @param timestamp      - timestamp of data
   * @return mapped WeatherDataEntry object
   */
  public WeatherDataEntry elementToWeatherDataEntry(Element stationElement,
      String stationName, Timestamp timestamp) throws ParsingException {
    // first check if all required data is present
    if (stationName == null || stationName.isBlank()) {
      throw new ParsingException("Station name absent!");
    }

    if (timestamp == null) {
      throw new ParsingException("Timestamp absent!");
    }

    String wmoCode = stationElement.getElementsByTagName("wmocode")
        .item(0).getTextContent();

    if (wmoCode.isBlank()) {
      throw new ParsingException("WMO code absent!");  // wmo code cannot be blank
    }

    // actual mapping
    CompositeKey compositeKey = new CompositeKey(stationName, timestamp);

    String airTemperatureText = stationElement
        .getElementsByTagName("airtemperature").item(0).getTextContent();
    Double airTemperature = null;

    if (airTemperatureText != null && !airTemperatureText.isBlank()) {
      airTemperature = Double.valueOf(airTemperatureText);
    }

    String windSpeedText = stationElement
        .getElementsByTagName("windspeed").item(0).getTextContent();
    Double windSpeed = null;

    if (windSpeedText != null && !windSpeedText.isBlank()) {
      windSpeed = Double.valueOf(windSpeedText);
    }

    String phenomenon = stationElement.getElementsByTagName("phenomenon")
        .item(0).getTextContent();

    return new WeatherDataEntry(compositeKey,
        wmoCode, airTemperature, windSpeed, phenomenon);
  }

}
