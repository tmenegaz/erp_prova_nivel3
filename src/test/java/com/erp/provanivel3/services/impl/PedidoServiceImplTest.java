package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.QCatalogo;
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

import static com.erp.provanivel3.common.ErpConstantes.*;
import static org.assertj.core.api.Assertions.assertThat;
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
        ))
                .thenReturn(Optional.of(PROD2));
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
        assertThat(IP2).isNotNull();

        for (ItemPedido ip: PED1.getItens()) {
            ip.setCatalogo(SERV1);
            ip.setCatalogo(PROD2);
        }
        Pedido sut = service.save(PED1);

        assertThat(sut.getItens()).isNotEmpty();



    }
}
