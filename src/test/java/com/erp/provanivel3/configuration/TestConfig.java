package com.erp.provanivel3.configuration;

import com.erp.provanivel3.services.ErpConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private ErpConstantes erpConstantes;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

}
