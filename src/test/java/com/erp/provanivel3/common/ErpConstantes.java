package com.erp.provanivel3.common;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.repositories.ItemPedidoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import com.erp.provanivel3.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.ScalarToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ErpConstantes {

    public ErpConstantes() {
    }

    public static final String ID = "186e787-63fc-484d-ac4b-30eb488d4407";
    public static final String IDFAKE = "f286e787-63fc-484d-ac4b-30eb488d4407";

    public static final Catalogo PROD1 = new Catalogo( UUID.fromString("a"+ID),
            "Tênis", 589.90, TipoCatalogo.PRODUTO, CondicaoProduto.DESATIVADO);
    public static final Catalogo PROD2 = new Catalogo( UUID.fromString("b"+ID),
            "Camisa", 78.59, TipoCatalogo.PRODUTO, CondicaoProduto.ATIVADO);
    public static final Catalogo SERV1 = new Catalogo( UUID.fromString("c"+ID),
            "Tinturaria", 59.75, TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);
    public static final Catalogo SERV2 = new Catalogo( UUID.fromString("d"+ID),
            "Pintura", 49.90, TipoCatalogo.SERVICO, CondicaoProduto.DESATIVADO);

    public static final Catalogo INVALID_CATALOG = new Catalogo( UUID.fromString("e"+ID),
            "", 0.0, null, null);

    public static final List<Catalogo> CATALOGOS = Arrays.asList(PROD2, SERV1);

    public static final Pedido PED1 = new Pedido(strToDate("30/09/2017 10:32"), StatusPedido.ABERTO);
    public static final Pedido PED2 = new Pedido(strToDate("30/09/2017 10:32"), StatusPedido.ABERTO);
    public static final Pedido PED3 = new Pedido(strToDate("30/09/2017 10:32"), StatusPedido.FECHADO);
    public static final Pedido PED4 = new Pedido(strToDate("30/09/2017 10:32"), StatusPedido.FECHADO);

    public static final List<Pedido> PEDIDOS = Arrays.asList(PED1);


    public static final ItemPedido IP2 = new ItemPedido(PED1, PROD2, 0.05, 2, 78.59);
    public static final ItemPedido IP3 = new ItemPedido(PED1, SERV1, 0.0, 2, 49.90);
    public static final ItemPedido IP4 = new ItemPedido(PED3, PROD1, 0.05, 2, 78.59);
    public static final ItemPedido IP5 = new ItemPedido(PED3, SERV2, 0.0, 2, 49.90);
    public static final ItemPedido IP6 = new ItemPedido(PED4, PROD2, 0.05, 2, 78.59);
    public static final ItemPedido IP7 = new ItemPedido(PED2, SERV1, 0.05, 2, 49.90);

    public static final List<ItemPedido> ITENS = Arrays.asList(IP2, IP3);

    private static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
