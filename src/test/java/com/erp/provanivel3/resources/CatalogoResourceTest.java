package com.erp.provanivel3.resources;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.DTO.CatalogoDTO;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.erp.provanivel3.common.CatalogoConstantes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        CatalogoResource.class,
        CatalogoServiceImpl.class
})
public class CatalogoResourceTest {

    @Autowired
    private CatalogoResource catalogoResource;

    @Autowired
    @Qualifier("catalogoServiceImpl")
    private CatalogoServiceImpl catalogoService;

    @MockBean
    private CatalogoRepository repository;

    @Before
    public void setup() {
        repository.saveAll(CATALOGOS);
        repository.save(PROD1);
        repository.save(PROD2);
        repository.save(SERV1);
        repository.save(SERV2);
    }

    @Test
    public void findCatalog_inCatalog_returnCatalog() throws Exception {

    }
}
