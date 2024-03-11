package com.fujitsu.fujitsutrialtask.repository;

import com.fujitsu.fujitsutrialtask.repository.entity.CompositeKey;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataEntryRepository extends JpaRepository<WeatherDataEntry, CompositeKey> {

}
