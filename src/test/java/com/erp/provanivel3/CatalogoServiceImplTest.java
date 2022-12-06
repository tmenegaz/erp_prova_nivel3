package com.erp.provanivel3;

import com.erp.provanivel3.domain.Catalogo;
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

    List<Catalogo> catalogos = Arrays.asList(
                new Catalogo(), new Catalogo(), new Catalogo(), new Catalogo()
        );

    @Test
    public void findAll() {
        List<Catalogo> list = service.findAll();
        Assertions.assertEquals(list.size(), catalogos.size());
    }

    @Before
    public void setup() {

        Catalogo prod1 = new Catalogo(
                "Tênis", 589.90,
                TipoCatalogo.PRODUTO, CondicaoProduto.DESATIVADO);
        Catalogo prod2 = new Catalogo(
                "Camisa", 78.59,
                TipoCatalogo.PRODUTO, CondicaoProduto.ATIVADO);

        Catalogo serv1 = new Catalogo(
                "Tinturaria", 59.75,
                TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);
        Catalogo serv2 = new Catalogo(
                "Pintura", 49.90,
                TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);


        List<Catalogo> list = Arrays.asList(
                prod1, prod2,
                serv1, serv2
        );
        Mockito.when(repository.findAll()).thenReturn(list);
    }
}
