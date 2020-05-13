package com.luxoft.springdb.lab1;

import com.luxoft.springdb.lab1.model.Country;
import com.luxoft.springdb.lab1.repository.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpringDataJPATest {

    @Autowired
    private CountryRepository countryRepository;

    private List<Country> expectedCountryList = new ArrayList<>();
    private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    public static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
            {"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
            {"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
            {"Russian Federation", "RU"}, {"Sweden", "SE"},
            {"Switzerland", "CH"}, {"United Kingdom", "GB"},
            {"United States", "US"}};

    @Before
    public void setUp() {
        initExpectedCountryLists();
    }

    @Test
    public void testCountryList() {
        List<Country> countryList = countryRepository.findAll();
        assertNotNull(countryList);
        assertEquals(COUNTRY_INIT_DATA.length, countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals(expectedCountryList.get(i), countryList.get(i));
        }
    }

    @Test
    public void testCountryListStartsWithA() {
        List<Country> countryList = countryRepository.retrieveCountryByName("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
        }
    }

    @Test
    public void testCountryChange() {
        countryRepository.updateCountryNameByCodeName("Russia", "RU");
        Country country = countryRepository.findByCodeName("RU");
        assertNotNull(country);
        assertEquals(country.getName(), "Russia");
    }

    private void initExpectedCountryLists() {
        for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = COUNTRY_INIT_DATA[i];
            Country country = new Country(i, countryInitData[0], countryInitData[1]);
            expectedCountryList.add(country);
            if (country.getName().startsWith("A")) {
                expectedCountryListStartsWithA.add(country);
            }
        }
    }
}