package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.repository.WeatherDataEntryRepository;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        properties = {"spring.datasource.url = jdbc:h2:file:./src/test/resources/data/schedulingTest",
                "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect"})
class WeatherDataServiceTest {

    @Autowired
    private WeatherDataEntryRepository entryRepository;
    @Autowired
    private WeatherDataService weatherDataService;

    @Test
    void testUpdateWeatherData() throws Exception {

        weatherDataService.updateWeatherData();

        List<WeatherDataEntry> entries = entryRepository.findAll();
        Assertions.assertEquals(3, entries.size());

        for (WeatherDataEntry entry : entries) {
            assertNotNull(entry, "Entry should not be null");
            assertNotNull(entry.getCompositeKey(), "Composite key should not be null");
            assertNotNull(entry.getCompositeKey().getStationName(), "Station name should not be null");
            assertNotNull(entry.getCompositeKey().getTimestamp(), "Timestamp should not be null");
            assertNotNull(entry.getWmoCode(), "WMO code should not be null");
        }

        entryRepository.deleteAll();
    }

}
