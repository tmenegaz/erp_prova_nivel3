package com.erp.provanivel3;

import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.QPedido;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import com.erp.provanivel3.services.impl.PedidoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PedidoServiceImplTest {

    @TestConfiguration
    static class PedidoServiceImplTestConfiguration {

        @Bean
        public PedidoServiceImpl PedidoServiceImpl() {
            return new PedidoServiceImpl();
        }
    }

    @Autowired
    @InjectMocks
    PedidoServiceImpl service;

    @MockBean
    PedidoRepository repository;

    @MockBean
    CatalogoRepository catalogoRepository;

    @Autowired
    CatalogoServiceImpl catalogoService;

    Pedido ped1;
    Pedido ped2;
    Pedido ped3;
    Pedido ped4;

    String id = "186e787-63fc-484d-ac4b-30eb488d4407";
    List<Pedido> pedidos;

    @Test
    public void findAllItens() {
        when(repository.findAll()).thenReturn(pedidos);

        List<Pedido> list = service.findAll();
        final List<Pedido> pedidos = list.stream()
                .filter(f -> f.getItens().size() > 0)
                .collect(Collectors.toList());
        Assertions.assertEquals(list.size(), pedidos.size());
    }

    @Test
    public void findById() {
        when(repository.findOne(
                QPedido.pedido.id.eq(ped1.getId())
        ))
                .thenReturn(Optional.of(ped1));

        Pedido obj = service.findById("a"+id);
        Assertions.assertEquals(obj, ped1);
    }

    @Test
    public void create() {
        when(repository.save(ped2)).thenReturn(ped2);

        Pedido obj = service.save(ped2);
        Assertions.assertEquals(obj, ped2);
    }

    @Test
    public void update() {
        when(repository.findOne(
                QPedido.pedido.id.eq(ped1.getId())
        ))
                .thenReturn(Optional.of(ped1));

        Pedido objF = service.findById(ped1.getId().toString());

        when(repository.save(objF)).thenReturn(ped1);

        Pedido obj = service.save(ped1);
        Assertions.assertEquals(obj, objF);
    }

    @Test
    public void deleteById() {
        when(repository.findOne(
                QPedido.pedido.id.eq(ped1.getId())
        ))
                .thenReturn(Optional.of(ped1))
                .thenReturn(null);

        service.deleteById("c"+id);
        verify(repository, times(1)).deleteById(UUID.fromString("c"+id));
    }

    @Before
    public void setup() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        ped1 = new Pedido(sdf.parse("30/09/2021 10:28"), StatusPedido.ABERTO);
        ped2 = new Pedido(sdf.parse("30/09/2021 10:28"), StatusPedido.ABERTO);
        ped3 = new Pedido(sdf.parse("30/09/2021 10:30"), StatusPedido.FECHADO);
        ped4 = new Pedido(sdf.parse("30/09/2021 10:31"), StatusPedido.FECHADO);


        repository.saveAll(Arrays.asList(
                ped1, ped2, ped3, ped4
        ));

    }
}
