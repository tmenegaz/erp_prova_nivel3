package com.erp.provanivel3.domain;

import com.erp.provanivel3.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@Column(nullable = false)
	private Date instante;

	@Min(1)
	@Max(2)
	@Column(nullable = false)
	private Integer status;

	@OneToMany(mappedBy = "id.pedido", cascade = CascadeType.REMOVE)
	private Set<ItemPedido> itens = new HashSet<>();

	public Pedido() {
	}

	public Pedido(Date instante, StatusPedido status) {
		this.instante = instante;
		this.status = (status == null) ? null : status.getCod();
	}

	public Pedido(UUID id, Date instante, StatusPedido status) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Pedido pedido = (Pedido) o;

		return new EqualsBuilder().append(getId(), pedido.getId()).append(getInstante(), pedido.getInstante()).append(getStatus(), pedido.getStatus()).append(getItens(), pedido.getItens()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}
}
