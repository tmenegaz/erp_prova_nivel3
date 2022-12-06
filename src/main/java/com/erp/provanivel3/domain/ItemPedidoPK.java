package com.erp.provanivel3.domain;

import com.erp.provanivel3.domain.exception.CondicaoException;
import com.erp.provanivel3.domain.exception.ValidationException;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class ItemPedidoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "catalogo_id")
	private Catalogo catalogo;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		try {
			ValidationException.ItemPedidoPk(catalogo);
			this.catalogo = catalogo;
		} catch (CondicaoException e) {
			e.getMessage();
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemPedidoPK that = (ItemPedidoPK) o;
		return getCatalogo().equals(that.getCatalogo()) && getPedido().equals(that.getPedido());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCatalogo(), getPedido());
	}
}
