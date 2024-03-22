package com.fujitsu.fujitsutrialtask.repository;

import com.fujitsu.fujitsutrialtask.repository.entity.CompositeKey;
import com.fujitsu.fujitsutrialtask.repository.entity.WeatherDataEntry;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherDataEntryRepository extends JpaRepository<WeatherDataEntry, CompositeKey> {

  @Query("select e from WeatherDataEntry e where e.compositeKey.stationName = ?1 order by e.compositeKey.timestamp desc limit 1")
  Optional<WeatherDataEntry> findLatestEntryByStation(String stationName);
}
