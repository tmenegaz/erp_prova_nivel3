package com.erp.provanivel3.domain;

import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.domain.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "itens_pedidos")
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
    @EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double desconto;
	private Integer quantidade;
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
				.map(ps -> ps.getCatalogo().getPreco() * (1 - ps.getDesconto()) * ps.getQuantidade())
				.reduce((a, b) -> a + b)
				.orElse(0.0);

		precoTotalServico = Arrays.asList(ip).stream()
				.filter(q -> q.getCatalogo() != null)
				.filter(s -> s.getCatalogo().getTipo().equals(TipoCatalogo.SERVICO))
				.map(s -> s.getPreco() * s.getQuantidade())
				.reduce((a, b) -> a + b)
				.orElse(0.0);

		precoTotalProduto = Arrays.asList(ip).stream()
				.filter(j -> j.getPedido() != null)
				.filter(a -> a.getPedido().getStatus().equals(StatusPedido.FECHADO))
				.filter(s -> s.getCatalogo().getTipo().equals(TipoCatalogo.PRODUTO))
				.map(s -> s.getPreco() * s.getQuantidade())
				.reduce((a, b) -> a + b)
				.orElse(0.0);

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
        return getId().equals(that.getId()) && getDesconto().equals(that.getDesconto()) && getQuantidade().equals(that.getQuantidade()) && getPreco().equals(that.getPreco());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
