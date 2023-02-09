package com.vmbears.challenge.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "compra")
public class Compra {
  public Compra() {
	}

  @Id
  @GeneratedValue
  private int id;

  @Column(name = "valor")
  private float valor;

  @ManyToOne
  @JoinColumn(name = "regiao_id")
  private Regiao regiao;
}

