package com.vmbears.challenge.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmbears.challenge.Entity.Agente;
import com.vmbears.challenge.Entity.Compra;
import com.vmbears.challenge.Entity.Geracao;
import com.vmbears.challenge.Entity.PrecoMedio;
import com.vmbears.challenge.Entity.Regiao;
import com.vmbears.challenge.Repository.AgentDataRepository;

@Service
public class AgentDataService {

	@Autowired
	private AgentDataRepository agentDataRepository;
    
    public AgentDataService() {
    }

    public void uploadFile(MultipartFile file) throws IOException {
      
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
    
    public void persistAgentes(Agentes agentes) throws Exception {
    	
    	for (Agentes.Agente agenteXml : agentes.getAgente()) {
    	    Agente agente = new Agente();
    	    agente.setCodigo(Integer.parseInt(agenteXml.getCodigo()));
    	    agente.setData(agenteXml.getData().toGregorianCalendar().getTime());
    	    List<Regiao> regioes = new ArrayList<>();
    	    for (Agentes.Agente.Regiao regiaoXml : agenteXml.getRegiao()) {
    	        Regiao regiao = new Regiao();
    	        regiao.setSigla(regiaoXml.getSigla());

    	        
    	        List<Geracao> listGeracaoJPA = new <Geracao>ArrayList();
    	        for (Iterator iterator = regiaoXml.getGeracao().getValor().iterator(); iterator.hasNext();) {
					Geracao geracaoXML = (Geracao) iterator.next();
					
					Geracao geracaoPJA = new Geracao();
					geracaoPJA.setRegiao(regiao);
					geracaoPJA.setValor(geracaoXML.getValor());
					listGeracaoJPA.add(geracaoPJA);
				}
    	        

    	        List<Compra> listCompraJPA = new <Compra>ArrayList();
    	        for (Iterator iterator = regiaoXml.getCompra().getValor().iterator(); iterator.hasNext();) {
					Compra compraXML = (Compra) iterator.next();
					
					Compra compraPJA = new Compra();
					compraPJA.setRegiao(regiao);
					compraPJA.setValor(compraXML.getValor());
					listCompraJPA.add(compraPJA);
				}
    	        
    	        
    	        List<PrecoMedio> listPrecoMedioJPA = new <PrecoMedio>ArrayList();
    	        for (Iterator iterator = regiaoXml.getPrecoMedio().getValor().iterator(); iterator.hasNext();) {
    	        	PrecoMedio precoMedioXML = (PrecoMedio) iterator.next();
					
    	        	PrecoMedio precoMedioPJA = new PrecoMedio();
					precoMedioPJA.setRegiao(regiao);
					precoMedioPJA.setValor(precoMedioXML.getValor());
					listPrecoMedioJPA.add(precoMedioPJA);
				}
    	        
    	        regiao.setGeracoes(listGeracaoJPA);
    	        regiao.setCompras(listCompraJPA);
    	        regiao.setPrecosMedios(listPrecoMedioJPA);
    	        regioes.add(regiao);
    	    }
    	    agente.setRegioes(regioes);
    	    

    	    // h2 database persist agente
    	    agentDataRepository.saveAndFlush(agente);
    	    
    	}
    }
    
}