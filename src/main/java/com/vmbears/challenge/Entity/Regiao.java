package com.vmbears.challenge.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "regiao")
public class Regiao {
	public Regiao() {

	}

	@Id
	@Column(name = "sigla")
	private String sigla;

	@ManyToOne
	@JoinColumn(name = "codigo")
	private Agente agente;

	@OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Geracao> geracoes;

	@OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Compra> compras;

	@OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PrecoMedio> precosMedios;
}
