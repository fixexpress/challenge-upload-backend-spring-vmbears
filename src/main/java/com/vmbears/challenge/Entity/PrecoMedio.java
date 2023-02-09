package com.vmbears.challenge.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "precomedio")
public class PrecoMedio {
  public PrecoMedio() {
	}

@Id
  private int id;

  @Column(name = "valor")
  private float valor;

  @ManyToOne
  @JoinColumn(name = "regiao_id")
  private Regiao regiao;
}

