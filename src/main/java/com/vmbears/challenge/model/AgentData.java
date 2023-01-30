package com.vmbears.challenge.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "agent_data")
public class AgentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "code")
    private Integer code;

    //@Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;

    @Column(name = "region")
    private String region;

    @Column(name = "geracao")
    private Double geracao;

    @Column(name = "compra")
    private Double compra;

    @Column(name = "preco_medio")
    private Double precoMedio;

	public AgentData() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Double getGeracao() {
		return geracao;
	}

	public void setGeracao(Double geracao) {
		this.geracao = geracao;
	}

	public Double getCompra() {
		return compra;
	}

	public void setCompra(Double compra) {
		this.compra = compra;
	}

	public Double getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
}
