package com.luxoft.springdb.lab1.dao;

import com.luxoft.springdb.lab1.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CountryDao {

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Country> getCountryList() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Country> countryList = jdbcTemplate.query("select * from country", COUNTRY_ROW_MAPPER);
        return countryList;
    }

    public List<Country> getCountryListStartWith(String name) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", name + "%");
        return namedParameterJdbcTemplate.query("select * from country where name like :name",
                sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update country SET name= ? where code_name= ?",
				newCountryName, codeName);
    }

    public Country getCountryByCodeName(String codeName) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("codeName", codeName);

        return namedParameterJdbcTemplate
				.query("select * from country where code_name = :codeName", sqlParameterSource, COUNTRY_ROW_MAPPER)
				.get(0);
    }
}
