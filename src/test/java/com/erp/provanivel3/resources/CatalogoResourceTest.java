package com.erp.provanivel3.resources;

import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.erp.provanivel3.services.ErpConstantes.FROMDTO;
import static com.erp.provanivel3.services.ErpConstantes.PRODDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
