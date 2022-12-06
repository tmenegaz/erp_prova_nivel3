package com.erp.provanivel3.domain.DTO;

import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

public class PedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	private Integer status;

	@JsonIgnore
	private Set<ItemPedido> itens = new HashSet<>();

	public PedidoDTO() {
	}

	public PedidoDTO(Pedido obj){
		this.id = obj.getId();
		this.instante = obj.getInstante();
		this.status = obj.getStatus().getCod();
		this.getItens().addAll(obj.getItens());
	}

	public PedidoDTO(Integer status) {
		this.status = status;
	}

	public PedidoDTO(Set<ItemPedido> itens){
		this.getItens().addAll(itens);
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
		PedidoDTO pedido = (PedidoDTO) o;
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
