package com.erp.provanivel3.domain;

import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.domain.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "itens_pedidos")
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    @Min(0)
    @Max(1)
    @Column(nullable = false)
    private Double desconto;

    @Min(1)
    @Column(nullable = false)
    private Integer quantidade;

    @Min(1)
    @Column(nullable = false)
    private Double preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Catalogo catalogo, Double desconto, Integer quantidade, Double preco) {
        id.setPedido(pedido);
        id.setCatalogo(catalogo);
        setDesconto(desconto, pedido, catalogo);
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Double getSubTotalDesconto(ItemPedido ip) {
        Double precoTotalProdutoComDesconto = 0.0;
        Double precoTotalServico = 0.0;
        Double precoTotalProduto = 0.0;

        precoTotalProdutoComDesconto = Arrays.asList(ip).stream()
                .filter(g -> g.getPedido() != null)
                .filter(a -> a.getPedido().getStatus().equals(StatusPedido.ABERTO))
                .filter(f -> f.getCatalogo().getTipo().equals(TipoCatalogo.PRODUTO))
                .map(ps -> (ps.getCatalogo().getPreco() * ps.getQuantidade()) * (1 - ps.getDesconto()))
                .reduce((a, b) -> a + b)
                .orElse(0.0);
        if (precoTotalProdutoComDesconto != 0.0) {
            for (ItemPedido iPreco: ip.getPedido().getItens()) {
                iPreco.setPreco(precoTotalProdutoComDesconto);
            }
        }

        precoTotalServico = Arrays.asList(ip).stream()
                .filter(q -> q.getCatalogo() != null)
                .filter(s -> s.getCatalogo().getTipo().equals(TipoCatalogo.SERVICO))
                .map(s -> s.getPreco() * s.getQuantidade())
                .reduce((a, b) -> a + b)
                .orElse(0.0);
        if (precoTotalServico != 0.0) {
            for (ItemPedido iPreco: ip.getPedido().getItens()) {
                iPreco.setPreco(precoTotalProdutoComDesconto);
            }
        }

        precoTotalProduto = Arrays.asList(ip).stream()
                .filter(j -> j.getPedido() != null)
                .filter(a -> a.getPedido().getStatus().equals(StatusPedido.FECHADO))
                .filter(s -> s.getCatalogo().getTipo().equals(TipoCatalogo.PRODUTO))
                .map(s -> s.getPreco() * s.getQuantidade())
                .reduce((a, b) -> a + b)
                .orElse(0.0);
        if (precoTotalProduto != 0.0) {
            for (ItemPedido iPreco: ip.getPedido().getItens()) {
                iPreco.setPreco(precoTotalProdutoComDesconto);
            }
        }

        return (precoTotalProdutoComDesconto + precoTotalServico + precoTotalProduto);
    }

    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }
    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

    public Catalogo getCatalogo() {
        return id.getCatalogo();
    }
    public void setCatalogo(Catalogo catalogo) {
        id.setCatalogo(catalogo);
    }

    public ItemPedidoPK getId() {
        return id;
    }

    public void setId(ItemPedidoPK id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto, Pedido pedido, Catalogo catalogo) {
        try {
            ValidationException.ItemPedido(desconto, pedido, catalogo);
            this.desconto = desconto;
        } catch (DescontoException e) {
            e.getMessage();
        }
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido that = (ItemPedido) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getDesconto(), that.getDesconto()).append(getQuantidade(), that.getQuantidade()).append(getPreco(), that.getPreco()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }
}
