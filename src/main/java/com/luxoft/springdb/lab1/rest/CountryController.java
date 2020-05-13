package com.luxoft.springdb.lab1.rest;

import com.luxoft.springdb.lab1.model.Country;
import com.luxoft.springdb.lab1.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class CountryController {

    @Autowired
    private CountryRestController restController;

    @Autowired
    private CountryRepository countryRepository;

    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("countrys", restController.get());
        return "index";
    }

    @RequestMapping(value = "/country/create",
            method = RequestMethod.GET)
    public String createFormCountry(Model model) {
        CountryDto countryDto = new CountryDto();
        model.addAttribute("countryDto", countryDto);
        return "createCountry";
    }

    @RequestMapping(value = "/country/update/{id}",
            method = RequestMethod.GET)
    public String updateCountry(Model model,
                                @PathVariable("id") int id) {
        Optional<Country> country = countryRepository.findById(id);
        if(country.isPresent()){
            model.addAttribute("countryDto", CountryDto.toDto(country.get()));
            return "createCountry";
        }

        CountryDto countryDto = new CountryDto();
        model.addAttribute("countryDto", countryDto);
        return "createCountry";
    }


    @RequestMapping(value = { "/country/create" }, method = RequestMethod.POST)
    public String createCountry(Model model,
                             @ModelAttribute("countryDto") CountryDto dto) {

        if(!dto.getCodeName().isEmpty() && !dto.getName().isEmpty()){
            restController.create(dto);
            return "redirect:/";
        }
        return "createCountry";
    }

    @RequestMapping(value = { "/country/del/{id}" }, method = RequestMethod.GET)
    public String delCountry(Model model,
                             @PathVariable("id") int id) {

        restController.delete(id);
        return "redirect:/";
    }
}
