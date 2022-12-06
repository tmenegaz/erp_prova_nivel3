package com.erp.provanivel3.domain.exception;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;

public class ValidationException {

    private ValidationException() {}

    public static void ItemPedidoPk(Catalogo produtoServico) throws CondicaoException {
        if (produtoServico.getCondicao().equals(CondicaoProduto.DESATIVADO)){
            throw new CondicaoException("Não é possível incluir o produto desativado no pedido", produtoServico.toString());
        }
    }

    public static void ItemPedido(Double desconto, Pedido pedido, Catalogo produtoServico) throws  DescontoException {
        if (pedido.getStatus().equals(StatusPedido.FECHADO)) {
            pedido.getItens().clear();
            throw new DescontoException("Não é possível oferecer desconto. O pedido está fechado", pedido.toString());
        }
        if (produtoServico.getTipo().equals(TipoCatalogo.SERVICO) && desconto > 0.0) {
            pedido.getItens().clear();
            throw new DescontoException("Não é possível oferecer desconto para servicos", produtoServico.toString());
        }
        if (pedido == null || produtoServico == null) {
            throw new NullPointerException("Não pode ser nullo");
        }
    }
}
