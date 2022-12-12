package com.erp.provanivel3.configuration;

import com.erp.provanivel3.services.DBService;
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

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.jpa.properties.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instatiateDataBase() throws ParseException {
        if ("dev".equals(profile)) {

            if (!"update".equals(strategy)) return false;

            dbService.instatiateDataBase();
            return true;
        }
        return false;
    }

}
