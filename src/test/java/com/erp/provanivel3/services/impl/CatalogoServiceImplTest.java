package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.QCatalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.services.exceptions.DataIntegrityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.erp.provanivel3.services.ErpConstantes.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogoServiceImplTest {

    @InjectMocks
    private CatalogoServiceImpl service;

    @Mock
    private CatalogoRepository repository;

    @Test
    public void criarCatalogo_ComDadosValidos_RetornaCatalogo() {
        when(repository.save(PROD2)).thenReturn(PROD2);

        Catalogo sut = service.save(PROD2);
        assertThat(sut).isEqualTo(PROD2);
    }

    @Test
    public void criarCatalogo_ComDadosInvalidos_RetornaCatalogo() {
        when(repository.save(INVALID_CATALOG))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(
                () -> service.save(INVALID_CATALOG)
        ).isInstanceOf(RuntimeException.class);
    }

    //      executar individualmente
    @Test
    public void acharCatalago_porId_retprnarUmCatalogado() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(PROD1.getId())
        ))
                .thenReturn(Optional.of(PROD1));

        Optional<Catalogo> sut = service.findById(String.valueOf(PROD1.getId()));
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PROD1);
    }

    @Test
    public void acharCatalago_porIdInexitente_retprnarUmCatalogado() {
        PROD2.setId(UUID.randomUUID());
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(PROD2.getId())
        )).thenReturn(Optional.empty());

        Optional<Catalogo> sut = service.findById(String.valueOf(PROD2.getId()));
        assertThat(sut).isEmpty();
    }

    @Test
    public void acharTodosCatalogos_RetornaTodos() {
        when(repository.findAll()).thenReturn(CATALOGOS);

        List<Catalogo> suts = service.findAll();

        assertThat(suts).isNotEmpty();
        assertThat(suts).hasSize(CATALOGOS.size());
        assertThat(suts.get(0)).isEqualTo(PROD2);
    }

    @Test
    public void acharTodosCatalogos_RetornaNenhum() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Catalogo> suts = service.findAll();

        assertThat(suts).isEmpty();
    }

    @Test
    public void atualizaCatalogo_comIdValido_retornaCatalogo() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(PROD1.getId())
        ))
                .thenReturn(Optional.of(PROD1));

        Optional<Catalogo> objF = service.findById(PROD1.getId().toString());
        assertThat(objF.get()).isNotNull();

        when(repository.save(objF.get())).thenReturn(PROD1);

        Catalogo sut = service.save(objF.get());
        assertThat(sut).isEqualTo(objF.get());
    }

    @Test
    public void atualizaCatalogo_comIdinvalido_retornaCatalogo() {


        when(repository.save(INVALID_CATALOG))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(
                () -> service.save(INVALID_CATALOG)
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void removeCatalogo_existId_naoRetornaException() {
         PROD1.setId(UUID.randomUUID());
        doNothing()
                .when(repository).deleteById(PROD1.getId());
        assertThatCode(() -> service.deleteById(String.valueOf(PROD1.getId())))
                .doesNotThrowAnyException();
    }

    @Test
    public void removeCatalogo_existId_retornaException() {
        doThrow(new DataIntegrityException("Não é possível excluir o produtos associado a um pedido"))
                .when(repository).deleteById(UUID.fromString(IDFAKE));
        assertThatCode(() -> service.deleteById(String.valueOf(UUID.fromString(IDFAKE))))
                .isInstanceOf(DataIntegrityException.class);
    }
}
