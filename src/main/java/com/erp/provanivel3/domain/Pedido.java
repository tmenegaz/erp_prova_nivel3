package com.erp.provanivel3.domain;

import com.erp.provanivel3.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy= "org.hibernate.id.UUIDGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(
							name = "id",
							value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
					)
			}
	)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	@NotNull(message="Preenchimento obrigatório")
	private Date instante;
	private Integer status;

	@OneToMany(mappedBy = "id.pedido", cascade = CascadeType.REMOVE)
	private Set<ItemPedido> itens = new HashSet<>();

	public Pedido() {
	}

	public Pedido(Date instante, StatusPedido status) {
		this.instante = instante;
		this.status = (status == null) ? null : status.getCod();
	}

	public Pedido(StatusPedido status) {
		this.status = (status==null) ? null : status.getCod();
	}

	public String getValorTotal() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		double soma = 0.0;
		for (ItemPedido ip : itens) {
			soma = soma + ip.getSubTotalDesconto(ip);
		}
		return nf.format(soma);
	}

	public StatusPedido getStatus() {
		return StatusPedido.toEnum(status);
	}
	public void setStatus(StatusPedido status) {
		this.status = status.getCod();
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pedido pedido = (Pedido) o;
		return getId().equals(pedido.getId()) && getInstante().equals(pedido.getInstante()) && getStatus().equals(pedido.getStatus());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

}
