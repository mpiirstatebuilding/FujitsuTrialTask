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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
class WeatherDataEntryMapperTests {
    @Autowired
    private WeatherDataEntryMapper mapper;
    private static String stationName = "Pakri";
    private static String wmoCode = "26029";
    private static String wmoCodeAbsent = "";
    private static String airTemp = "2.9";
    private static String windSpeed = "2.5";
    private static String phenomenon = "Mist";
    private static Timestamp timestamp = new Timestamp(Long.parseLong("1711020886000"));
    private static Element stationElement = mock(Element.class);
    private static Element stationElementNoWmoCode = mock(Element.class);

    @BeforeAll
    static void initializeStationElement() throws ParserConfigurationException, IOException, SAXException {
        String stationXml = """
            <station>
                      <name>Pakri</name>
                      <wmocode>26029</wmocode>
                      <phenomenon>Mist</phenomenon>
                      <airtemperature>2.9</airtemperature>
                      <windspeed>2.5</windspeed>
                      </station>
                      """;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(stationXml)));

        stationElement = (Element) document.getElementsByTagName("station").item(0);

        String stationXmlNoWmo = """
            <station>
                      <name>Pakri</name>
                      <wmocode/>
                      <phenomenon>Mist</phenomenon>
                      <airtemperature>2.9</airtemperature>
                      <windspeed>2.5</windspeed>
                      </station>
                      """;

        document = builder.parse(new InputSource((new StringReader(stationXmlNoWmo))));
        stationElementNoWmoCode = (Element) document.getElementsByTagName("station").item(0);
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
