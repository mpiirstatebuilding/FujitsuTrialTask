package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.repository.WeatherDataEntryRepository;
import com.fujitsu.fujitsutrialtask.repository.entity.CompositeKey;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.DeliveryFeeException;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.WeatherConditionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryFeeServiceTests {

    @Autowired
    private DeliveryFeeService deliveryFeeService;

    @MockBean
    private WeatherDataEntryRepository weatherDataEntryRepository;
    WeatherDataEntry entry = new WeatherDataEntry(new CompositeKey("Tallinn-Harku", new Timestamp(1710616610)),
            "26038", 10.0, 1.0, "");

    @Test
    void testTallinnBaseFees() {
        weatherDataEntryRepository.save(entry);

        WeatherDataEntry weatherDataEntry = weatherDataEntryRepository.getReferenceById(new CompositeKey("Tallinn-Harku", new Timestamp(1710616610)));

        Float expectedCarFee = 4.0f;
        Float expectedScooterFee = 3.5f;
        Float expectedBikeFee = 3.0f;

        try {
            Assertions.assertEquals(expectedCarFee,
                    deliveryFeeService.getDeliveryFee( "Tallinn", "Car", null));
            Assertions.assertEquals(expectedScooterFee,
                    deliveryFeeService.getDeliveryFee( "Tallinn", "Scooter", null));
            Assertions.assertEquals(expectedBikeFee,
                    deliveryFeeService.getDeliveryFee( "Tallinn", "Bike", null));
        } catch (DeliveryFeeException | WeatherConditionException e) {
            Assertions.fail("Threw exception: " + e);
        }
    }

}
