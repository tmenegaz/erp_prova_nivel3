package com.erp.provanivel3.services;

import java.util.List;

public interface EntityService {

    List<?> findAll();

    void deleteById(String id);

}
