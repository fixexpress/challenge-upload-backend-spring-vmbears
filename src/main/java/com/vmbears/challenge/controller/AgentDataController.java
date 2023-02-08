package com.vmbears.challenge.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vmbears.challenge.Service.AgentDataService;

@RestController
public class AgentDataController {

    private final AgentDataService agentDataService;

    @Autowired
    public AgentDataController(AgentDataService agentDataService) {
        this.agentDataService = agentDataService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    	agentDataService.uploadFile(file);
    }

//    @GetMapping("/get-consolidated-data-by-region")
//    public Map<String, List<AgentData>> getConsolidatedDataByRegion() {
//        return agentDataService.getConsolidatedDataByRegion();
//    }
}

