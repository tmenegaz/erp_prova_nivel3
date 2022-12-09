package com.erp.provanivel3.common;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CatalogoConstantes {

    public static final String ID = "186e787-63fc-484d-ac4b-30eb488d4407";

    public static final Catalogo PROD1 = new Catalogo( UUID.fromString("a"+ID),
                "Tênis", 589.90, TipoCatalogo.PRODUTO, CondicaoProduto.DESATIVADO);
    public static final Catalogo PROD2 = new Catalogo( UUID.fromString("b"+ID),
                "Camisa", 78.59, TipoCatalogo.PRODUTO, CondicaoProduto.ATIVADO);
    public static final Catalogo SERV1 = new Catalogo( UUID.fromString("c"+ID),
                "Tinturaria", 59.75, TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);
    public static final Catalogo SERV2 = new Catalogo( UUID.fromString("d"+ID),
                "Pintura", 49.90, TipoCatalogo.SERVICO, CondicaoProduto.ATIVADO);


    public static final List<Catalogo> CATALOGOS = Arrays.asList(
                PROD1, PROD2,
                SERV1, SERV2
        );

}
