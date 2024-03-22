package com.fujitsu.fujitsutrialtask.service;

import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.DeliveryFeeException;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.WeatherConditionException;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
    properties = {"spring.datasource.url = jdbc:h2:file:./src/test/resources/data/weatherDataTest",
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect"})
class DeliveryFeeServiceTests {

  @Autowired
  private DeliveryFeeService deliveryFeeService;

  @Test
  void testTallinnBaseFees() {
    Float expectedCarFee = 4.0f;
    Float expectedScooterFee = 3.5f;
    Float expectedBikeFee = 3.0f;

    try {
      String timestampString = "1710692100"; // 2024-03-17 18:15:00 GMT+2
      String city = "Tallinn";
      Assertions.assertEquals(expectedCarFee,
          deliveryFeeService.getDeliveryFee(city, "Car", timestampString));
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testTartuBaseFees() {
    Float expectedCarFee = 3.5f;
    Float expectedScooterFee = 3.0f;
    Float expectedBikeFee = 2.5f;

    try {
      String timestampString = "1710692100"; // 2024-03-17 18:15:00 GMT+2
      String city = "Tartu";
      Assertions.assertEquals(expectedCarFee,
          deliveryFeeService.getDeliveryFee(city, "Car", timestampString));
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testPaernuBaseFees() {
    Float expectedCarFee = 3.0f;
    Float expectedScooterFee = 2.5f;
    Float expectedBikeFee = 2.0f;

    try {
      String timestampString = "1710692100"; // 2024-03-17 18:15:00 GMT+2
      String city = "Paernu";
      Assertions.assertEquals(expectedCarFee,
          deliveryFeeService.getDeliveryFee(city, "Car", timestampString));
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testAtefLessThanMinusTen() {
    Float expectedScooterFee = 4.5f; // tallin-scooter fee + airtemp < -10 fee = 3.5 + 1 = 4.5
    Float expectedBikeFee = 4.0f; // tallin-bike fee + airtemp < -10 fee = 3 + 1 = 4

    try {
      String timestampString = "1710695700"; // 2024-03-17 19:15:00 GMT+2
      String city = "Tallinn";
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testAtefBetweenMinusTenAndZero() {
    Float expectedScooterFee = 3.5f; // tartu-scooter fee + 0 > airtemp > -10 fee = 3 + 0.5 = 3.5
    Float expectedBikeFee = 3.0f; // tartu-bike fee + 0 > airtemp > -10 fee = 2.5 + 0.5 = 3

    try {
      String timestampString = "1710695700"; // 2024-03-17 19:15:00 GMT+2
      String city = "Tartu";
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testWsefBetweenTenAndTwenty() {
    Float expectedBikeFee = 2.5f; // pärnu-bike fee + 20 > wind speed > 10 fee = 2 + 0.5 = 2.5

    try {
      String timestampString = "1710695700"; // 2024-03-17 19:15:00 GMT+2
      String city = "Paernu";
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testWsefOverTwenty() {
    Assertions.assertThrows(WeatherConditionException.class,
        () -> deliveryFeeService.getDeliveryFee("Tallinn", "Bike", "1710699300"));
  }

  @Test
  void testPhenomenonSnow() {
    Float expectedScooterFee = 3.5f; // pärnu-scooter fee + snow fee = 2.5 + 1 = 3.5
    Float expectedBikeFee = 3.0f; // pärnu-bike fee + snow fee = 2 + 1 = 3

    try {
      String timestampString = "1710699300"; // 2024-03-17 20:15:00 GMT+2
      String city = "Paernu";
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testPhenomenonSleet() {
    Float expectedScooterFee = 4.5f; // tallinn-scooter fee + sleet fee = 3.5 + 1 = 4.5
    Float expectedBikeFee = 4.0f; // tallinn-bike fee + sleet fee = 3 + 1 = 4

    try {
      String timestampString = "1710702900"; // 2024-03-17 21:15:00 GMT+2
      String city = "Tallinn";
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testPhenomenonRain() {
    Float expectedScooterFee = 3.5f; // tartu-scooter fee + rain fee = 3 + 0.5 = 3.5
    Float expectedBikeFee = 3.0f; // tartu-bike fee + rain fee = 2.5 + 0.5 = 3

    try {
      String timestampString = "1710702900"; // 2024-03-17 21:15:00 GMT+2
      String city = "Tartu";
      Assertions.assertEquals(expectedScooterFee,
          deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertEquals(expectedBikeFee,
          deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception: " + e);
    }
  }

  @Test
  void testForbiddenPhenomena() {
    String timestampStringPaernu = "1710702900"; // 2024-03-17 21:15:00 GMT+2
    String timestampStringTallinnTartu = "1710706500"; // 2024-03-17 22:15:00 GMT+2

    String cityGlaze = "Paernu";
    String cityHail = "Tallinn";
    String cityThunder = "Tartu";

    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(cityGlaze, timestampStringPaernu);
    parameters.put(cityHail, timestampStringTallinnTartu);
    parameters.put(cityThunder, timestampStringTallinnTartu);

    for (String city : parameters.keySet()) {
      String timestampString = parameters.get(city);
      Assertions.assertThrows(WeatherConditionException.class,
          () -> deliveryFeeService.getDeliveryFee(city, "Scooter", timestampString));
      Assertions.assertThrows(WeatherConditionException.class,
          () -> deliveryFeeService.getDeliveryFee(city, "Bike", timestampString));
    }
  }

  @Test
  void testNoTimestampGivenGetsLatestEntry() {
    Float expectedTallinnBikeFee = 4.0f; // tallinn fee + 20 > wind speed > 10 fee + rain fee = 3 + 0.5 + 0.5 = 4
    Float expectedTartuBikeFee = 3.0f; // tartu fee + 0 > airtemp > -10 fee = 2.5 + 0.5 = 3

    try {
      Assertions.assertEquals(expectedTallinnBikeFee,
          deliveryFeeService.getDeliveryFee("Tallinn", "Bike", null));
      Assertions.assertEquals(expectedTartuBikeFee,
          deliveryFeeService.getDeliveryFee("tartu", "Bike", null));
      Assertions.assertThrows(WeatherConditionException.class,
          () -> deliveryFeeService.getDeliveryFee("paernu", "Bike", null));
    } catch (DeliveryFeeException | WeatherConditionException e) {
      Assertions.fail("Threw exception at wrong time: " + e);
    }
  }
}
