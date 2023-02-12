package com.vmbears.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vmbears.challenge.Service.AgentDataService;
import com.vmbears.challenge.Service.RegiaoConsolidada;

@RestController
public class AgentDataController {

    private final AgentDataService agentDataService;

    @Autowired
    public AgentDataController(AgentDataService agentDataService) {
        this.agentDataService = agentDataService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file[]) throws Exception {
    	agentDataService.uploadFile(file);
    }

    @GetMapping("/get-consolidated-data-by-region")
    public ResponseEntity<List<RegiaoConsolidada>> getConsolidatedDataByRegion() {
        List<RegiaoConsolidada> consolidatedData = agentDataService.listarDadoConsolidadoPorRegiao();
        return new ResponseEntity<>(consolidatedData, HttpStatus.OK);
    }
    
}

