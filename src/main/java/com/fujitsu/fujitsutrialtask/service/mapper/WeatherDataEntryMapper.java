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
        String wmoCode = stationElement.getElementsByTagName("wmocode").item(0).getTextContent();

        if (wmoCode.isBlank()) throw new ParsingException("WMO code absent!");  // wmo code cannot be null

        Double airTemperature = Double.valueOf(stationElement
                .getElementsByTagName("airtemperature").item(0).getTextContent());
        Double windSpeed = Double.valueOf(stationElement
                .getElementsByTagName("windspeed").item(0).getTextContent());
        String phenomenon = stationElement.getElementsByTagName("phenomenon").item(0).getTextContent();

        return new WeatherDataEntry(new CompositeKey(stationName, timestamp),
                wmoCode, airTemperature, windSpeed, phenomenon);
    }

}
