package com.erp.provanivel3.domain.exception;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;

public class ValidationException {

    private ValidationException() {}

    public static void ItemPedidoPk(Catalogo catalogo) throws CondicaoException {
        if (catalogo.getCondicao().equals(CondicaoProduto.DESATIVADO)){
            throw new CondicaoException("Não é possível incluir o produto desativado no pedido", catalogo.toString());
        }
    }

    public static void ItemPedido(Double desconto, Pedido pedido, Catalogo catalogo) throws  DescontoException {
        if (pedido.getStatus().equals(StatusPedido.FECHADO)) {
            pedido.getItens().clear();
            throw new DescontoException("Não é possível oferecer desconto. O pedido está fechado", pedido.toString());
        }
        if (catalogo.getTipo().equals(TipoCatalogo.SERVICO) && desconto > 0.0) {
            pedido.getItens().clear();
            throw new DescontoException("Não é possível oferecer desconto para servicos", catalogo.toString());
        }
        if (pedido == null || catalogo == null) {
            throw new NullPointerException("Não pode ser nullo");
        }
    }
}
