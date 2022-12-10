package com.erp.provanivel3.resources;

import com.erp.provanivel3.repositories.CatalogoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CatalogoResourceTest {

    @InjectMocks
    private CatalogoResource catalogoResource;

    @Mock
    private CatalogoRepository repository;


    @Test
    public void findCatalog_inCatalog_returnCatalog() {
//        when()

    }
}
