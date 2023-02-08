package com.vmbears.challenge.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmbears.challenge.Entity.Agentes;

@Service
public class AgentDataService {

    //@Autowired
    //private AgentDataRepository agentDataRepository;
    
    public AgentDataService() {
    }

    public void uploadFile(MultipartFile file) throws IOException {
      
       // Path path = Paths.get(file.toString());
        
        try {
            Agentes agentes = readDataFromMultipart(file);            
            System.out.println("Arquivo: "+file.getOriginalFilename());
            System.out.println("Qtd.Agentes: "+agentes.getAgente().size());      
            for (int i = 0; i < agentes.getAgente().size(); i++) {
            	System.out.println("Códigos do agente : "+agentes.getAgente().get(i).getCodigo());
            	System.out.println("data: "+agentes.getAgente().get(i).getData());
			}
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Agentes readDataFromMultipart(MultipartFile multipart) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Agentes.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Agentes agentes = (Agentes) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(multipart.getBytes()));
        return agentes;    
    }      
    
}