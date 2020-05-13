package com.luxoft.springdb.lab1.rest;

import com.luxoft.springdb.lab1.model.Country;
import com.luxoft.springdb.lab1.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryRestController {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryRestController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @RequestMapping(
            value = "/country",
            method = RequestMethod.GET
    )
    public List<CountryDto> get() {
        return countryRepository.findAll().stream()
                .map(CountryDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/country/{id}",
            method = RequestMethod.GET
    )
    public CountryDto getCountryById(
            @PathVariable("id") int id
    ) {
        Country country = countryRepository.findById(id).orElseThrow(NotFoundException::new);
        return CountryDto.toDto(country);
    }

    @RequestMapping(
            value = "/country/",
            method = RequestMethod.POST
    )
    public @ResponseBody
    CountryDto create(
            @RequestBody CountryDto dto
    ) {
        if(dto.getId() != null ){
            countryRepository.updateCountryById(dto.getName(), dto.getCodeName(), dto.getId());
            return dto;
        } else {
            Country country = CountryDto.toDomainObject(dto);
            Country countryWithId = countryRepository.save(country);
            return CountryDto.toDto(countryWithId);
        }
    }

    @DeleteMapping("/country/{id}")
    public void delete(@PathVariable("id") int id) {
        countryRepository.deleteById(id);
    }

    @PutMapping("/country/{id}/holder")
    public void changeNameAndCodeName(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("codeName") String codeName
    ) {
        Country country = countryRepository.findById(id).orElseThrow(NotFoundException::new);
        country.setName(name);
        country.setCodeName(codeName);
        countryRepository.save(country);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotEnoughFunds(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Not found");
    }
}
