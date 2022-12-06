package com.erp.provanivel3;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.QCatalogo;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.repository.CatalogoRepository;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    Catalogo prod3;
    Catalogo prod4;
    Catalogo serv1;
    Catalogo serv2;
    Catalogo serv3;
    Catalogo serv4;

    String id = "186e787-63fc-484d-ac4b-30eb488d4407";
    List<Catalogo> catalogos = Arrays.asList(
                new Catalogo(), new Catalogo(), new Catalogo(), new Catalogo()
        );

    @Test
    public void findAll() {
        List<Catalogo> list = service.findAll();
        Assertions.assertEquals(list.size(), catalogos.size());
    }

    @Test
    public void findById() {
        Catalogo obj = service.findById("a"+id);
        Assertions.assertEquals(obj, prod1);
    }

    @Test
    public void create() {
        Catalogo obj = service.save(prod2);
        Assertions.assertEquals(obj, prod2);
    }

    @Before
    public void setup() {

        prod1 = new Catalogo( UUID.fromString("a"+id),
                "Tênis", 589.90,
                TipoCatalogo.PRODUTO, CondicaoProduto.DESATIVADO);
        prod2 = new Catalogo( UUID.fromString("b"+id),
                "Camisa", 78.59,
                TipoCatalogo.PRODUTO, CondicaoProduto.ATIVADO);

        serv1 = new Catalogo( UUID.fromString("c"+id),
                "Tinturaria", 59.75,
                TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);
        serv2 = new Catalogo( UUID.fromString("d"+id),
                "Pintura", 49.90,
                TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);


        List<Catalogo> list = Arrays.asList(
                prod1, prod2,
                serv1, serv2
        );

        repository.saveAll(list);

        Mockito.when(repository.findAll()).thenReturn(list);

        Mockito.when(repository.findOne(
                QCatalogo.catalogo.id.eq(prod1.getId())
                )).thenReturn(Optional.of(prod1));

        Mockito.when(repository.save(prod2)).thenReturn(prod2);
    }
}
