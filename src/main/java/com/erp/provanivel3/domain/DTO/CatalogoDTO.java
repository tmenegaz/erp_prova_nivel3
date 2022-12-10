package com.erp.provanivel3.domain.DTO;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

public class CatalogoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	@Digits(integer=6, fraction=2, message = "Preenchimento obrigatório")
	@Min(0)
	private Double preco;

	private Integer tipo;
	private Integer condicao;

	@JsonIgnore
	private Set<ItemPedido> itens = new HashSet<>();

	public CatalogoDTO() {
	}

	public CatalogoDTO(UUID id, String nome, Double preco, TipoCatalogo tipo, CondicaoProduto condicao) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.tipo = (tipo==null) ? null : tipo.getCod();
		this.condicao = (condicao == null) ? null : condicao.getCod();
	}

	public CatalogoDTO(Catalogo obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.preco = obj.getPreco();
		this.tipo = (obj.getTipo()==null) ? null : obj.getTipo().getCod();
		this.condicao = (obj.getCondicao() == null) ? null : obj.getCondicao().getCod();
	}

	public TipoCatalogo getTipo() {
		return TipoCatalogo.toEnum(tipo);
	}
	public void setTipo(TipoCatalogo tipo) {
		this.tipo = tipo.getCod();
	}

	public CondicaoProduto getCondicao() {
		return CondicaoProduto.toEnum(condicao);
	}
	public void setCondicao(CondicaoProduto condicao) {
		this.condicao = condicao.getCod();
	}

	@JsonIgnore
	public List<Pedido> getPedidos() {
		List<Pedido> lista = new ArrayList<>();
		for (ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

		CatalogoDTO that = (CatalogoDTO) o;

		return new EqualsBuilder().append(getId(), that.getId()).append(getNome(), that.getNome()).append(getPreco(), that.getPreco()).append(getTipo(), that.getTipo()).append(getCondicao(), that.getCondicao()).append(getItens(), that.getItens()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}
}
