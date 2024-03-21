package com.fujitsu.fujitsutrialtask.service.mapper;

import com.fujitsu.fujitsutrialtask.repository.entity.CompositeKey;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ParsingException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.sql.Timestamp;

@Component
public class WeatherDataEntryMapper {

    public WeatherDataEntry elementToWeatherDataEntry(Element stationElement, String stationName, Timestamp timestamp) throws ParsingException {
        if (stationName == null || stationName.isBlank()) throw new ParsingException("Station name absent!");
        if (timestamp == null) throw new ParsingException("Timestamp absent!");

        String wmoCode = stationElement.getElementsByTagName("wmocode").item(0).getTextContent();

        if (wmoCode.isBlank()) throw new ParsingException("WMO code absent!");  // wmo code cannot be null

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

        String phenomenon = stationElement.getElementsByTagName("phenomenon").item(0).getTextContent();

        return new WeatherDataEntry(compositeKey,
                wmoCode, airTemperature, windSpeed, phenomenon);
    }

}
