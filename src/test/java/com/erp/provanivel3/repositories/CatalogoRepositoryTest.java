package com.erp.provanivel3.repositories;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.QCatalogo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.Optional;

import static com.erp.provanivel3.services.ErpConstantes.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class CatalogoRepositoryTest {

    @Autowired
    private CatalogoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void criarCatalogo_ComDadosValidos_RetornaCatalogo() {
        Catalogo catalogo = repository.save(PROD3);

        Catalogo sut = entityManager.find(
                Catalogo.class, catalogo.getId()
        );

        assertThat(sut).isNotNull();
        assertThat(sut.getNome()).isEqualTo(PROD3.getNome());
        assertThat(sut.getPreco()).isEqualTo(PROD3.getPreco());
        assertThat(sut.getTipo()).isEqualTo(PROD3.getTipo());
        assertThat(sut.getCondicao()).isEqualTo(PROD3.getCondicao());
    }


}
