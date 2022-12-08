package com.erp.provanivel3;

import com.erp.provanivel3.resources.CatalogoResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CatalogoResource.class)
@AutoConfigureMockMvc
public class CatalogoResourceTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void findAllTest() throws Exception {
        mvc.perform(get("/produtosservicos"))
                .andExpect(status().isOk());
    }
}
