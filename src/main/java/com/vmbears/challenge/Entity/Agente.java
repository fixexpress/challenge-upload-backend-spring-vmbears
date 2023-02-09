package com.vmbears.challenge.Entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agente")
public class Agente {
  public Agente() {
		
	}

@Id
  @Column(name = "codigo")
  private int codigo;

  @Column(name = "data")
  @Temporal(TemporalType.TIMESTAMP)
  private Date data;

  @OneToMany(mappedBy = "agente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Regiao> regioes;
}
