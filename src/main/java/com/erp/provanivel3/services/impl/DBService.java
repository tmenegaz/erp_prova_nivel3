package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.repository.CatalogoRepository;
import com.erp.provanivel3.repository.ItemPedidoRepository;
import com.erp.provanivel3.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBService {

    @Autowired
    private CatalogoRepository catalogoRespository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;


    public void instatiateDataBase() throws ParseException {
        Catalogo prod1 = new Catalogo(
                "Tênis", 589.90,
                TipoCatalogo.PRODUTO,
                CondicaoProduto.DESATIVADO);
        Catalogo prod2 = new Catalogo(
                "Camisa", 78.59,
                TipoCatalogo.PRODUTO,
                CondicaoProduto.ATIVADO);
        Catalogo prod3 = new Catalogo(
                "Cinto", 39.90,
                TipoCatalogo.PRODUTO,
                CondicaoProduto.DESATIVADO);
        Catalogo prod4 = new Catalogo(
                "Sapato", 249.90,
                TipoCatalogo.PRODUTO,
                CondicaoProduto.ATIVADO);
        Catalogo prod5 = new Catalogo(
                "Chinelo", 89.89,
                TipoCatalogo.PRODUTO,
                CondicaoProduto.ATIVADO);
        Catalogo serv1 = new Catalogo(
                "Tinturaria", 59.75,
                TipoCatalogo.SERVICO,
                CondicaoProduto.ATIVADO);
        Catalogo serv2 = new Catalogo(
                "Pintura", 49.90,
                TipoCatalogo.SERVICO,
                CondicaoProduto.ATIVADO);
        Catalogo serv3 = new Catalogo(
                "Costura", 29.90,
                TipoCatalogo.SERVICO,
                CondicaoProduto.ATIVADO);
        Catalogo serv4 = new Catalogo(
                "Marcenaria", 58.20,
                TipoCatalogo.SERVICO,
                CondicaoProduto.ATIVADO);
        Catalogo serv5 = new Catalogo(
                "Ensino", 92.30,
                TipoCatalogo.SERVICO,
                CondicaoProduto.ATIVADO);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), StatusPedido.ABERTO);
        Pedido ped2 = new Pedido(sdf.parse("30/09/2017 10:32"), StatusPedido.ABERTO);
        Pedido ped3 = new Pedido(sdf.parse("30/09/2017 10:32"), StatusPedido.FECHADO);
        Pedido ped4 = new Pedido(sdf.parse("30/09/2017 10:32"), StatusPedido.FECHADO);

        catalogoRespository.saveAll(Arrays.asList(
                prod1, prod2, prod3, prod4, prod5, serv1, serv2, serv3, serv4, serv5
        ));

        ItemPedido ip1 = new ItemPedido(ped1, prod3, 0.05, 2, 39.90);
        ItemPedido ip2 = new ItemPedido(ped2, prod1, 0.05, 3, 589.90);
        ItemPedido ip3 = new ItemPedido(ped1, prod2, 0.05, 2, 78.59);
        ItemPedido ip4 = new ItemPedido(ped4, prod4, 0.0, 3, 249.90);
        ItemPedido ip5 = new ItemPedido(ped2, prod3, 0.05, 2, 39.90);
        ItemPedido ip6 = new ItemPedido(ped3, serv1, 0.05, 1, 59.75);
        ItemPedido ip7 = new ItemPedido(ped1, serv2, 0.0, 2, 49.90);
        ItemPedido ip8 = new ItemPedido(ped2, serv3, 0.05, 3, 29.90);
        ItemPedido ip9 = new ItemPedido(ped3, serv4, 0.0, 4, 58.20);
        ItemPedido ip10 = new ItemPedido(ped3, serv5, 0.0, 5, 92.30);

        List<ItemPedido> allItens = Arrays.asList(ip1, ip2, ip3, ip4, ip5, ip6, ip7, ip8, ip9, ip10);
        List<ItemPedido> itens = allItens.stream()
                .filter(f -> f.getCatalogo() != null)
                .collect(Collectors.toList());


        ped1.getItens().addAll(Arrays.asList(ip3, ip7));
        ped2.getItens().addAll(Arrays.asList(ip8));
        ped3.getItens().addAll(Arrays.asList(ip6, ip9, ip10));
        ped4.getItens().addAll(Arrays.asList(ip4));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3, ped4));

        serv1.getItens().addAll(Arrays.asList(ip6));
        serv2.getItens().addAll(Arrays.asList(ip7));
        serv3.getItens().addAll(Arrays.asList(ip8));
        serv4.getItens().addAll(Arrays.asList(ip9));
        serv5.getItens().addAll(Arrays.asList(ip10));

        prod2.getItens().addAll(Arrays.asList(ip3));
        prod4.getItens().addAll(Arrays.asList(ip4));

        System.out.println(ped1);

        itemPedidoRepository.saveAll(Arrays.asList(
                ip3, ip4, ip6, ip7, ip8, ip9, ip10
        ));

    }
}
