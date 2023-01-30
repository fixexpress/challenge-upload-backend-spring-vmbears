package com.vmbears.challenge.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmbears.challenge.model.AgentData;
import com.vmbears.challenge.repository.AgentDataRepository;

@Service
public class AgentDataService {

    @Autowired
    private AgentDataRepository agentDataRepository;

    public void uploadFile(MultipartFile file) throws IOException {
    	
    	Path path = Paths.get(file.toString());
        try {
            List<AgentData> agentDataList = readDataFromFile(path.toString());
            System.out.println("Arquivo : "+path);
            
            for (AgentData agentData : agentDataList) {
            	System.out.println("código agente -> "+agentData.getCode());	
                agentDataRepository.save(agentData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AgentData> readDataFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(AgentDataListWrapper.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        AgentDataListWrapper agentDataListWrapper = (AgentDataListWrapper) jaxbUnmarshaller.unmarshal(file);
        return agentDataListWrapper.getAgentData();
    }    
    

    @XmlRootElement(name = "agentDataList")
    private static class AgentDataListWrapper {
        private List<AgentData> agentData;

        @XmlElement(name = "agentData")
        public List<AgentData> getAgentData() {
            return agentData;
        }
    }
    
    public Map<String, List<AgentData>> getConsolidatedDataByRegion() {
        List<AgentData> allData = agentDataRepository.findAll();
        Map<String, List<AgentData>> consolidatedData = new HashMap<>();
        for (AgentData data : allData) {
            String region = data.getRegion();
            if (!consolidatedData.containsKey(region)) {
                consolidatedData.put(region, new ArrayList<AgentData>());
            }
            consolidatedData.get(region).add(data);
        }
        return consolidatedData;
    }
}

