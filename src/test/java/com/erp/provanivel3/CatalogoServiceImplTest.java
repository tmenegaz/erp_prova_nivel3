package com.erp.provanivel3;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.QCatalogo;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CatalogoServiceImplTest {

    @TestConfiguration
    static class CatalogoServiceImplTestConfiguration {

        @Bean
        public CatalogoServiceImpl CatalogoServiceImpl() {
            return new CatalogoServiceImpl();
        }
    }


    @Autowired
    CatalogoServiceImpl service;

    @MockBean
    CatalogoRepository repository;

    Catalogo prod1;
    Catalogo prod2;
    Catalogo serv1;
    Catalogo serv2;

    String id = "186e787-63fc-484d-ac4b-30eb488d4407";
    List<Catalogo> catalogos;

    @Test
    public void findAll() {
        when(repository.findAll()).thenReturn(catalogos);

        List<Catalogo> list = service.findAll();
        Assertions.assertEquals(list.size(), catalogos.size());
    }

    @Test
    public void findById() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(prod1.getId())
        ))
                .thenReturn(Optional.of(prod1));

        Catalogo obj = service.findById("a"+id);
        Assertions.assertEquals(obj, prod1);
    }

    @Test
    public void create() {
        when(repository.save(prod2)).thenReturn(prod2);

        Catalogo obj = service.save(prod2);
        Assertions.assertEquals(obj, prod2);
    }

    @Test
    public void update() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(prod1.getId())
        ))
                .thenReturn(Optional.of(prod1));

        Catalogo objF = service.findById(prod1.getId().toString());

        when(repository.save(objF)).thenReturn(prod1);

        Catalogo obj = service.save(prod1);
        Assertions.assertEquals(obj, objF);
    }

    @Test
    public void deleteById() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(serv1.getId())
        ))
                .thenReturn(Optional.of(prod1))
                .thenReturn(null);

        service.deleteById("c"+id);
        verify(repository, times(1)).deleteById(UUID.fromString("c"+id));
    }

    @Before
    public void setup() {

        prod1 = new Catalogo( UUID.fromString("a"+id),
                "Tênis", 589.90, TipoCatalogo.PRODUTO, CondicaoProduto.DESATIVADO);
        prod2 = new Catalogo( UUID.fromString("b"+id),
                "Camisa", 78.59, TipoCatalogo.PRODUTO, CondicaoProduto.ATIVADO);

        serv1 = new Catalogo( UUID.fromString("c"+id),
                "Tinturaria", 59.75, TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);
        serv2 = new Catalogo( UUID.fromString("d"+id),
                "Pintura", 49.90, TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);

        catalogos = Arrays.asList(
                prod1, prod2,
                serv1, serv2
        );

        repository.saveAll(catalogos);

    }
}
