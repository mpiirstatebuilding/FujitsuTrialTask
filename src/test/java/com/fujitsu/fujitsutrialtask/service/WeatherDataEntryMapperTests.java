package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ParsingException;
import com.fujitsu.fujitsutrialtask.service.mapper.WeatherDataEntryMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Timestamp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class WeatherDataEntryMapperTests {
    @Autowired
    private WeatherDataEntryMapper mapper;
    private static String stationName = "Pakri";
    private static String wmoCode = "26029";
    private static String airTemp = "2.9";
    private static String windSpeed = "2.5";
    private static String phenomenon = "Mist";
    private static Timestamp timestamp = new Timestamp(Long.parseLong("1711020886000"));
    private static Element stationElement = mock(Element.class);
    private static Element stationElementNoWmoCode = mock(Element.class);

    @BeforeAll
    static void initializeStationElement() {
        NodeList mockNodeList = mock(NodeList.class);
        when(mockNodeList.item(0)).thenReturn(mock(Node.class)); // Simulate one item in the list

        when(stationElement.getElementsByTagName("wmocode")).thenReturn(mockNodeList);
        when(stationElement.getElementsByTagName("airtemperature")).thenReturn(mockNodeList);
        when(stationElement.getElementsByTagName("windspeed")).thenReturn(mockNodeList);
        when(stationElement.getElementsByTagName("phenomenon")).thenReturn(mockNodeList);

        when(stationElementNoWmoCode.getElementsByTagName("wmocode")).thenReturn(mockNodeList);
        when(stationElementNoWmoCode.getElementsByTagName("airtemperature")).thenReturn(mockNodeList);
        when(stationElementNoWmoCode.getElementsByTagName("windspeed")).thenReturn(mockNodeList);
        when(stationElementNoWmoCode.getElementsByTagName("phenomenon")).thenReturn(mockNodeList);

        when(stationElement.getElementsByTagName("wmocode").item(0).getTextContent()).thenReturn(wmoCode);
        when(stationElement.getElementsByTagName("airtemperature").item(0).getTextContent()).thenReturn(airTemp);
        when(stationElement.getElementsByTagName("windspeed").item(0).getTextContent()).thenReturn(windSpeed);
        when(stationElement.getElementsByTagName("phenomenon").item(0).getTextContent()).thenReturn(phenomenon);

        when(stationElementNoWmoCode.getElementsByTagName("wmocode").item(0).getTextContent()).thenReturn("");
        when(stationElementNoWmoCode.getElementsByTagName("airtemperature").item(0).getTextContent()).thenReturn(airTemp);
        when(stationElementNoWmoCode.getElementsByTagName("windspeed").item(0).getTextContent()).thenReturn(windSpeed);
        when(stationElementNoWmoCode.getElementsByTagName("phenomenon").item(0).getTextContent()).thenReturn(phenomenon);
    }

    @Test
    void testThrowsExceptionIfNameAbsent() {
        Assertions.assertThrows(ParsingException.class, () -> mapper.elementToWeatherDataEntry(stationElement, "", timestamp));
        Assertions.assertThrows(ParsingException.class, () -> mapper.elementToWeatherDataEntry(stationElement, null, timestamp));
    }

    @Test
    void testThrowsExceptionIfTimestampAbsent() {
        Assertions.assertThrows(ParsingException.class, () -> mapper.elementToWeatherDataEntry(stationElement, stationName, null));
    }

    @Test
    void testThrowsExceptionIfWmoCodeAbsent() {
        Assertions.assertThrows(ParsingException.class, () -> mapper.elementToWeatherDataEntry(stationElementNoWmoCode, stationName, timestamp));
    }

}
