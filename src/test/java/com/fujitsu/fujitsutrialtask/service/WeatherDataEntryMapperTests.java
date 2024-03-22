package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ParsingException;
import com.fujitsu.fujitsutrialtask.service.mapper.WeatherDataEntryMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
    private static final WeatherDataEntryMapper mapper = new WeatherDataEntryMapper();
    private static final String stationName = "Pakri";
    private static final String wmoCode = "26029";
    private static final Double airTemp = 2.9;
    private static final Double windSpeed = 2.5;
    private static final String phenomenon = "Mist";
    private static final Timestamp timestamp = new Timestamp(Long.parseLong("1711020886000"));
    private static Element stationElement = mock(Element.class);
    private static Element stationElementNoWmoCode = mock(Element.class);
    private static WeatherDataEntry normalEntry;

    @BeforeAll
    static void initializeStationElement() throws ParserConfigurationException, IOException, SAXException, ParsingException {
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

        normalEntry = mapper.elementToWeatherDataEntry(stationElement, stationName, timestamp);
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

    @Test
    void testDataMappedCorrectly() {
        Assertions.assertEquals(stationName, normalEntry.getCompositeKey().getStationName());
        Assertions.assertEquals(timestamp, normalEntry.getCompositeKey().getTimestamp());
        Assertions.assertEquals(wmoCode, normalEntry.getWmoCode());
        Assertions.assertEquals(airTemp, normalEntry.getAirTemperature());
        Assertions.assertEquals(windSpeed, normalEntry.getWindSpeed());
        Assertions.assertEquals(phenomenon, normalEntry.getPhenomenon());
    }

}
