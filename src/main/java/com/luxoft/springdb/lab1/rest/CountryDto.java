package com.luxoft.springdb.lab1.rest;

import com.luxoft.springdb.lab1.model.Country;

public class CountryDto {

    private Integer id;

    private String name;

    private String codeName;

    public CountryDto() {
    }

    public CountryDto(int id, String name, String codeName) {
        this.id = id;
        this.name = name;
        this.codeName = codeName;
    }

    public CountryDto(String name, String codeName) {
        this.name = name;
        this.codeName = codeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String toString() {
        return id + ". " + name + " (" + codeName + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryDto country = (CountryDto) o;

        if (codeName != null ? !codeName.equals(country.codeName) : country.codeName != null) return false;
        if (name != null ? !name.equals(country.name) : country.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (codeName != null ? codeName.hashCode() : 0);
        return result;
    }

    public static Country toDomainObject(CountryDto dto) {
        if(dto.getId() != null) {
            return new Country(dto.getId(), dto.getName(), dto.getCodeName());
        }
        return new Country(dto.getName(), dto.getCodeName());
    }

    public static CountryDto toDto(Country country) {
        return new CountryDto(country.getId(), country.getName(), country.getCodeName());
    }
}
