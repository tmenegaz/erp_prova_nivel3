package com.erp.provanivel3;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.QPedido;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import com.erp.provanivel3.services.impl.PedidoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
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
    @TestConfiguration
    static class CatalogoServiceImplTestConfiguration {

        @Bean
        public CatalogoServiceImpl CatalogoServiceImpl() {
            return new CatalogoServiceImpl();
        }
    }


    @Autowired
    CatalogoServiceImpl catalogoService;

    @MockBean
    CatalogoRepository catalogoRepository;


    @Autowired
    PedidoServiceImpl pedidoService;

    @MockBean
    PedidoRepository pedidoRepository;

    Catalogo prod1;
    Catalogo prod2;
    Catalogo serv1;
    Catalogo serv2;

    Pedido ped1;
    Pedido ped2;
    Pedido ped3;
    Pedido ped4;

    String id = "186e787-63fc-484d-ac4b-30eb488d4407";
    List<Catalogo> catalogos;
    List<Pedido> pedidos;

    @Test
    public void findAll() {
        when(pedidoService.findAll()).thenReturn(pedidos);

        List<Pedido> list = pedidoService.findAll();
        Assertions.assertEquals(list.size(), pedidos.size());
    }

    @Test
    public void findById() {
        when(pedidoRepository.findOne(
                QPedido.pedido.id.eq(ped1.getId())
        ))
                .thenReturn(Optional.of(ped1));

        Pedido obj = pedidoService.findById("a"+id);
        Assertions.assertEquals(obj, ped1);
    }

    @Test
    public void create() {
        when(pedidoRepository.save(ped2)).thenReturn(ped2);

        Pedido obj = pedidoService.save(ped2);
        Assertions.assertEquals(obj, ped2);
    }

    @Test
    public void update() {
        when(pedidoRepository.findOne(
                QPedido.pedido.id.eq(ped1.getId())
        ))
                .thenReturn(Optional.of(ped1));

        Pedido objF = pedidoService.findById(ped1.getId().toString());

        when(pedidoRepository.save(objF)).thenReturn(ped1);

        Pedido obj = pedidoService.save(ped1);
        Assertions.assertEquals(obj, objF);
    }

    @Test
    public void deleteById() {
        when(pedidoRepository.findOne(
                QPedido.pedido.id.eq(serv1.getId())
        ))
                .thenReturn(Optional.of(ped1))
                .thenReturn(null);

        pedidoService.deleteById("c"+id);
        verify(pedidoRepository, times(1)).deleteById(UUID.fromString("c"+id));
    }

    @Before
    public void setup() throws ParseException {

        prod1 = new Catalogo( UUID.fromString("a"+id),
                "Tênis", 589.90, TipoCatalogo.PRODUTO, CondicaoProduto.DESATIVADO);
        serv1 = new Catalogo( UUID.fromString("c"+id),
                "Tinturaria", 79.75, TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);
        catalogos = Arrays.asList(prod1, serv1);

        catalogoRepository.saveAll(catalogos);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        ped1 = new Pedido(sdf.parse("30/09/2021 10:28"), StatusPedido.ABERTO);
        ped2 = new Pedido(sdf.parse("30/09/2021 10:28"), StatusPedido.ABERTO);
        ped3 = new Pedido(sdf.parse("30/09/2021 10:30"), StatusPedido.FECHADO);
        ped4 = new Pedido(sdf.parse("30/09/2021 10:31"), StatusPedido.FECHADO);

        ItemPedido ip1 = new ItemPedido(ped1, serv1, 0.05, 2, 79.75);
        ItemPedido ip2 = new ItemPedido(ped2, prod1, 0.05, 3, 589.90);

        pedidoRepository.saveAll(Arrays.asList(
                ped1, ped2, ped3, ped4
        ));

    }
}
