package com.vmbears.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vmbears.challenge.model.AgentData;

public interface AgentDataRepository extends JpaRepository<AgentData, Integer> {

}
