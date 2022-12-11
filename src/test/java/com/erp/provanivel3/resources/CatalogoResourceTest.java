package com.erp.provanivel3.resources;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

import static com.erp.provanivel3.services.ErpConstantes.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogoResource.class)
public class CatalogoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CatalogoServiceImpl service;

    @Test
    public void criarCatalogo_ComDadosValidos_RetornaStatus() throws Exception {
        when(service.fromDTO(PRODDTO)).thenReturn(FROMDTO);

        when(service.save(FROMDTO)).thenReturn(FROMDTO);

        mockMvc
                .perform(
                        post("/produtosservicos")
                                .content(objectMapper.writeValueAsString(FROMDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("http://localhost/produtosservicos/" + FROMDTO.getId()));
    }

}
