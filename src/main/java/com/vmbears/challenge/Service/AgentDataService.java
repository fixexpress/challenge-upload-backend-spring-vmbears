package com.vmbears.challenge.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
			System.out.println("Arquivo: " + file.getOriginalFilename());
			System.out.println("Qtd.Agentes: " + agentes.getAgente().size());
			for (int i = 0; i < agentes.getAgente().size(); i++) {
				System.out.println("Códigos do agente : " + agentes.getAgente().get(i).getCodigo());
				System.out.println("data: " + agentes.getAgente().get(i).getData());

				this.persistAgente(agentes.getAgente().get(i));
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

	public void persistAgente(Agentes.Agente agenteXML) throws Exception {
    	
    	System.out.println("Persist agente");
    	
    	// agenteDB
	    Agente agente = new Agente();
	    agente.setCodigo(Integer.parseInt(agenteXML.getCodigo()));
	    agente.setData(agenteXML.getData().toGregorianCalendar().getTime());
	    
	    // regioesDB
	    List<Regiao> regioes = new ArrayList<>();
	    List<Agentes.Agente.Regiao> listRegioes = agenteXML.getRegiao();
	    for (int i = 0; i < listRegioes.size(); i++) {

	    	//regiaoDB
	    	Regiao regiao = new Regiao();	    	
	    	regiao.setSigla(listRegioes.get(i).getSigla());
	    	regiao.setAgente(agente);
	    	
	    	
	    	//geracoesDB
	    	List<Geracao> listGeracao = new <Geracao>ArrayList();
			for (int k = 0; k < listRegioes.get(i).getGeracao().getValor().size(); k++) {
	    		//geracaoDB
				Geracao geracao = new Geracao();
				geracao.setValor(listRegioes.get(i).getGeracao().getValor().get(k));
				geracao.setRegiao(regiao);				
				listGeracao.add(geracao);					
			}
	    	
	    	//CompraDB
	    	List<Compra> listCompra = new <Compra>ArrayList();
			for (int k = 0; k < listRegioes.get(i).getCompra().getValor().size(); k++) {
	    		//compraDB
				Compra compra = new Compra();
				compra.setRegiao(regiao);
				compra.setValor(listRegioes.get(i).getCompra().getValor().get(k));
				listCompra.add(compra);					
			}

	    	//PrecoMedioDB
	    	List<PrecoMedio> listPrecoMedio = new <PrecoMedio>ArrayList();
			for (int k = 0; k < listRegioes.get(i).getPrecoMedio().getValor().size(); k++) {
	    		//precomedioDB
				PrecoMedio precoMedio = new PrecoMedio();
				precoMedio.setRegiao(regiao);
				precoMedio.setValor(listRegioes.get(i).getPrecoMedio().getValor().get(k));
				listPrecoMedio.add(precoMedio);					
			}
    	
	    	regiao.setGeracoes(listGeracao);
	    	regiao.setCompras(listCompra);
	    	regiao.setPrecosMedios(listPrecoMedio);
	    	
	    	regioes.add(regiao);
 	    	
		}

	    
    	agente.setRegioes(regioes);
	    agentDataRepository.save(agente);
	    
    }

}