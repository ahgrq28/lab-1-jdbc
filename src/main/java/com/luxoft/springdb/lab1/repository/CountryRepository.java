package com.luxoft.springdb.lab1.repository;

import com.luxoft.springdb.lab1.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);

    Country findByCodeName(String codeName);

    @Modifying
    @Query("update Country c set c.name = :name where c.codeName = :codeName")
    void updateCountryNameByCodeName(String name, String codeName);

    @Modifying
    @Query("update Country c set c.name = :name, c.codeName = :codeName where c.id = :id")
    void updateCountryById(String name, String codeName, Integer id);

    @Query("Select c from Country c where c.name like %:name%")
    List<Country> retrieveCountryByName(String name);
}
