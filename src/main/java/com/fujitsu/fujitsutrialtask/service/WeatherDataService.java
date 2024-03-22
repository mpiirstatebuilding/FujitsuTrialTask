package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.repository.WeatherDataEntryRepository;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ParsingException;
import com.fujitsu.fujitsutrialtask.service.mapper.WeatherDataEntryMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherDataService {

  @Value("${weather-data.source}")
  private String apiUrl;
  @Value("#{'${weather-data.required-stations}'.split(',')}")
  private List<String> requiredStations;
  final WeatherDataEntryMapper mapper;
  final WeatherDataEntryRepository entryRepository;

  @Scheduled(cron = "${weather-data.update-cron}")
  void updateWeatherData() throws ParsingException {

    // parsing
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {
      // for avoiding attacks
      dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new URL(apiUrl).openStream());

      doc.getDocumentElement().normalize(); // only one text node in element

      // get timestamp
      String timestampString = doc.getDocumentElement().getAttribute("timestamp");
        if (timestampString.isBlank()) {
            throw new ParsingException("No timestamp provided!"); // timestamp cannot be null
        }
      long timestampLong = Long.parseLong(timestampString) * 1000;
      Timestamp timestamp = new Timestamp(timestampLong);

      // get stations
      NodeList stationList = doc.getElementsByTagName("station");
      List<WeatherDataEntry> newEntries = new ArrayList<>();

      int index = 0;
      Node station;
      String stationName;

      while (index < stationList.getLength()) {
        station = stationList.item(index);
        Element stationElement = (Element) station;
        stationName = stationElement.getElementsByTagName("name").item(0).getTextContent();

          if (stationName.isBlank()) {
              throw new ParsingException("Station name is absent!");
          }

        if (this.requiredStations.contains(stationName)) {
          newEntries.add(mapper.elementToWeatherDataEntry(stationElement, stationName, timestamp));
        }
        index++;
      }

      entryRepository.saveAll(newEntries);

    } catch (ParserConfigurationException | IOException | SAXException | ParsingException e) {
      throw new ParsingException(e.getMessage());
    }

    log.info("Weather data updated.");
  }
}
