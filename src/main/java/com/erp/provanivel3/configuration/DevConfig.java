package com.erp.provanivel3.configuration;

import com.erp.provanivel3.services.impl.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instatiateDataBase() throws ParseException {
        if (!"update".equals(strategy)) return false;

        dbService.instatiateDataBase();
        return true;
    }

}