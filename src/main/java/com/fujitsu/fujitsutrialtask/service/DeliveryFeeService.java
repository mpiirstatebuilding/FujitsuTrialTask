package com.fujitsu.fujitsutrialtask.service;

import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService {

    public enum City {
        Tallinn("Tallinn-Harku", 4.0),
        Tartu("Tartu-Tõravere", 3.5),
        Parnu("Pärnu", 2.5);

        final String station;
        final Double RBF;

        City(String station, Double RBF) {
            this.station = station;
            this.RBF = RBF;
        }
    }

    public enum Vehicle {
        Car(0.0), Scooter(0.5), Bike(1.0);
        final Double RBFSubtract; // this will be subtracted from the city's RBF

        Vehicle(Double RBFSubtract) {
            this.RBFSubtract = RBFSubtract;
        }
    }

}
