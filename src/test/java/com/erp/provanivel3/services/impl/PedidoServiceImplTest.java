package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.QCatalogo;
import com.erp.provanivel3.domain.exception.CondicaoException;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.repositories.ItemPedidoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Optional;

import static com.erp.provanivel3.services.ErpConstantes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private PedidoRepository repository;

    @InjectMocks
    private CatalogoServiceImpl catalogoService;

    @Mock
    private CatalogoRepository catalogoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;


    @Test
    public void criarPedido_ComDadosValidos_RetornaPedido() {

        when(catalogoRepository.findOne(
                QCatalogo.catalogo.id.eq(PROD2.getId())
        )).thenReturn(Optional.of(PROD2));
        assertThat(PROD2).isNotNull();

        when(catalogoRepository.findOne(
                QCatalogo.catalogo.id.eq(SERV1.getId())
        )).thenReturn(Optional.of(SERV1));
        assertThat(SERV1).isNotNull();

        PED1.getItens().addAll(Arrays.asList(IP2, IP3));

        when(repository.save(PED1)).thenReturn(PED1);

        SERV1.getItens().addAll(Arrays.asList(IP3));
        PROD2.getItens().addAll(Arrays.asList(IP2));

        when(itemPedidoRepository.save(IP2)).thenReturn(IP2);
        assertThat(IP2).isNotNull();
        when(itemPedidoRepository.save(IP3)).thenReturn(IP3);
        assertThat(IP3).isNotNull();

        for (ItemPedido ip: PED1.getItens()) {
            ip.setCatalogo(SERV1);
            ip.setCatalogo(PROD2);
        }
        Pedido sut = service.save(PED1);

        assertThat(sut.getItens()).isNotEmpty();
        assertThat(sut).isEqualTo(PED1);

    }

    @Test
    public void criarPedido_ComStatusdeItemFechado_RetornaException() {

        when(catalogoRepository.findOne(
                QCatalogo.catalogo.id.eq(PROD1.getId())
        )).thenReturn(Optional.of(PROD1));
        assertThat(PROD1).isNotNull();

        when(catalogoRepository.findOne(
                QCatalogo.catalogo.id.eq(SERV2.getId())
        )).thenReturn(Optional.of(SERV2));
        assertThat(SERV2).isNotNull();

        PED3.getItens().addAll(Arrays.asList(IP4, IP5));

        when(repository.save(PED3)).thenReturn(PED3);

        SERV2.getItens().addAll(Arrays.asList(IP5));
        PROD1.getItens().addAll(Arrays.asList(IP4));

        when(itemPedidoRepository.save(IP4)).thenReturn(IP4);
        assertThat(IP4).isNotNull();
        when(itemPedidoRepository.save(IP5)).thenReturn(IP5);
        assertThat(IP5).isNotNull();

        for (ItemPedido ip: PED3.getItens()) {
            ip.setCatalogo(SERV2);
            assertThat(ip.getCatalogo()).isNull();
            ip.setCatalogo(PROD1);
            assertThat(ip.getCatalogo()).isNull();
        }
        when(repository.save(PED3))
                .thenThrow(CondicaoException.class);

        assertThatThrownBy(
                () -> service.save(PED3)
        ).isInstanceOf(CondicaoException.class);


    }

    @Test
    public void criarPedido_ComDescontoPedidoFechado_RetornaException() {

        when(catalogoRepository.findOne(
                QCatalogo.catalogo.id.eq(PROD2.getId())
        )).thenReturn(Optional.of(PROD2));
        assertThat(PROD2).isNotNull();

        PED4.getItens().addAll(Arrays.asList(IP6));

        when(repository.save(PED4)).thenReturn(PED4);

        PROD2.getItens().addAll(Arrays.asList(IP6));

        when(itemPedidoRepository.save(IP6)).thenReturn(IP6);
        assertThat(IP6).isNotNull();

        for (ItemPedido ip: PED4.getItens()) {
            ip.setCatalogo(PROD2);
            ip.setDesconto(ip.getDesconto(), PED4, ip.getCatalogo() );
            assertThat(ip.getDesconto()).isNull();
        }
        when(repository.save(PED4))
                .thenThrow(DescontoException.class);

        assertThatThrownBy(
                () -> service.save(PED4)
        ).isInstanceOf(DescontoException.class);


    }

    @Test
    public void criarPedido_ComDescontoServico_RetornaException() {

        when(catalogoRepository.findOne(
                QCatalogo.catalogo.id.eq(SERV1.getId())
        )).thenReturn(Optional.of(SERV1));
        assertThat(SERV1).isNotNull();

        PED2.getItens().addAll(Arrays.asList(IP7));

        when(repository.save(PED2)).thenReturn(PED2);

        SERV1.getItens().addAll(Arrays.asList(IP7));

        when(itemPedidoRepository.save(IP7)).thenReturn(IP7);
        assertThat(IP7).isNotNull();

        for (ItemPedido ip: PED2.getItens()) {
            ip.setCatalogo(SERV1);
            assertThat(ip.getDesconto()).isNull();
        }
        when(repository.save(PED2))
                .thenThrow(DescontoException.class);

        assertThatThrownBy(
                () -> service.save(PED2)
        ).isInstanceOf(DescontoException.class);

    }
}
