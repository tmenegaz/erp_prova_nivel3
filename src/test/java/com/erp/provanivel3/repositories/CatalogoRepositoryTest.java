package com.erp.provanivel3.repositories;

import com.erp.provanivel3.domain.Catalogo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.erp.provanivel3.services.ErpConstantes.PROD3;
import static org.assertj.core.api.Assertions.*;

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

    @Test
    public void criarCatalogo_ComDadosInvalidos_RetornaNull() {
        Catalogo empty = new Catalogo();
        Catalogo invalid = new Catalogo("", 0.0, null, null);

        Throwable throwableEmpty = catchThrowable(() -> repository.save(empty));
        assertThat(throwableEmpty).isNull();

        Throwable throwableInvalid = catchThrowable(() -> repository.save(invalid));
        assertThat(throwableInvalid).isNull();

    }


}
