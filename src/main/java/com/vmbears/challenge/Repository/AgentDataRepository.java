package com.vmbears.challenge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vmbears.challenge.Entity.Agente;

public interface AgentDataRepository extends JpaRepository<Agente, Integer> {

}
