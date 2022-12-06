package com.erp.provanivel3.domain;

import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "catalogos")
public class Catalogo implements Serializable {
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
	private String nome;
	private Double preco;
	private Integer tipo;
	private Integer condicao;

	@JsonIgnore
	@OneToMany(mappedBy = "id.catalogo")
	private Set<ItemPedido> itens = new HashSet<>();


	public Catalogo() {
	}

	public Catalogo(String nome, Double preco, TipoCatalogo tipo, CondicaoProduto condicao) {
		this.nome = nome;
		this.preco = preco;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		this.condicao = (condicao == null) ? null : condicao.getCod();
	}

	public Catalogo(UUID id, String nome, Double preco, TipoCatalogo tipo, CondicaoProduto condicaoProduto) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.tipo = (tipo==null) ? null : tipo.getCod();
		this.condicao = (condicaoProduto == null) ? null : condicaoProduto.getCod();
	}

	@JsonIgnore
	public List<Pedido> getPedidos() {
		List<Pedido> lista = new ArrayList<>();
		for (ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
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
		Catalogo catalogo = (Catalogo) o;
		return getId().equals(catalogo.getId()) && getNome().equals(catalogo.getNome()) && getPreco().equals(catalogo.getPreco()) && getTipo().equals(catalogo.getTipo()) && getCondicao().equals(catalogo.getCondicao()) && getItens().equals(catalogo.getItens());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
