package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.QCatalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.erp.provanivel3.common.CatalogoConstantes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogoServiceImplTest {

    @InjectMocks
    CatalogoServiceImpl service;

    @Mock
    CatalogoRepository repository;

    @Before
    public void setup() {
        repository.saveAll(CATALOGOS);
    }

    @Test
    public void create() {
        when(repository.save(PROD2)).thenReturn(PROD2);

        Catalogo obj = service.save(PROD2);
        Assertions.assertEquals(obj, PROD2);
    }

//      executar individualmente
    @Test
    public void findById() {
        for (Catalogo c: CATALOGOS){
            when(repository.findOne(
                    QCatalogo.catalogo.id.eq(c.getId())
            ))
                    .thenReturn(Optional.of(c));

            Catalogo obj = service.findById(String.valueOf(c.getId()));
            Assertions.assertEquals(obj, c);
        }
    }

    @Test
    public void findAll() {
        when(repository.findAll()).thenReturn(CATALOGOS);

        List<Catalogo> list = service.findAll();
        Assertions.assertEquals(list.size(), CATALOGOS.size());
    }

    @Test
    public void update() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(PROD1.getId())
        ))
                .thenReturn(Optional.of(PROD1));

        Catalogo objF = service.findById(PROD1.getId().toString());

        when(repository.save(objF)).thenReturn(PROD1);

        Catalogo obj = service.save(PROD1);
        Assertions.assertEquals(obj, objF);
    }

    @Test
    public void deleteById() {
        when(repository.findOne(
                QCatalogo.catalogo.id.eq(SERV1.getId())
        ))
                .thenReturn(Optional.of(SERV1));

        service.deleteById(String.valueOf(SERV1.getId()));
        verify(repository, times(1)).deleteById(
                UUID.fromString(
                        String.valueOf(SERV1.getId())
                ));
    }
}
