package com.erp.provanivel3.resources;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static com.erp.provanivel3.services.ErpConstantes.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CatalogoResourceTest {

    @Autowired
    private CatalogoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

     @Test
    public void criarCatalogo_ComDadosValidos_RetornaCatalogo() {
         Catalogo catalogo = repository.save(PROD2);

         Catalogo sut = entityManager.find(
                 Catalogo.class, catalogo.getId()
         );

         assertThat(sut).isNotNull();
     }


}
