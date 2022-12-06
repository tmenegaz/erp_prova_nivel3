package com.erp.provanivel3;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.resources.CatalogoResource;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

@SpringBootTest
class Provanivel3ApplicationTests {

    @Autowired
    private CatalogoResource catalogoResource;

    @MockBean
    private CatalogoServiceImpl service;


    private static final String catalogo = "http://localhost:8081/produtosservicos";
    private static final String pedido =  "http://localhost:8081/pedido";

    @BeforeEach
    public void setup() {
        standaloneSetup(catalogoResource);
    }

    @Test
    public void createProdORserv() {

        Catalogo obj = new Catalogo(
                "Tênis", 589.90,
                TipoCatalogo.PRODUTO,
                CondicaoProduto.DESATIVADO);
        when(service.save(obj)).thenReturn(obj);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(catalogo)
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }
    @Test
    public void listAll() {
        List<Catalogo> list = new ArrayList<>();
        when(service.findAll()).thenReturn(list);
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(catalogo)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
