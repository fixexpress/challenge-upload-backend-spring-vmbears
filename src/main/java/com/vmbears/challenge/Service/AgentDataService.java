package com.vmbears.challenge.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmbears.challenge.Entity.Agente;
import com.vmbears.challenge.Entity.Compra;
import com.vmbears.challenge.Entity.Geracao;
import com.vmbears.challenge.Entity.PrecoMedio;
import com.vmbears.challenge.Entity.Regiao;
import com.vmbears.challenge.Repository.AgentDataRepository;
import com.vmbears.challenge.Repository.RegiaoDataRepository;

@Service
public class AgentDataService {

	@Autowired
	private AgentDataRepository agentDataRepository;

	@Autowired
	private RegiaoDataRepository regiaoDataRepository;

	
	public AgentDataService() {
	}

	public void uploadFile(MultipartFile file[]) throws IOException {

		try {

			for (int i = 0; i < file.length; i++) {

				String xmlContent = new String(file[i].getBytes(), StandardCharsets.UTF_8);
				//System.out.println("Conteúdo do arquivo XML: /n" + xmlContent);
				
				Agentes agentes = readDataFromMultipart(file[i]);
				System.out.println("Arquivo: " + file[i].getOriginalFilename());
				System.out.println("Qtd.Agentes: " + agentes.getAgente().size());
				for (int x = 0; x < agentes.getAgente().size(); x++) {
					System.out.println("Códigos do agente : " + agentes.getAgente().get(x).getCodigo());
					System.out.println("data: " + agentes.getAgente().get(x).getData());

					this.persistAgente(agentes.getAgente().get(x));
				}
				
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

	    
//TODO: JUNIT class teste case -  only for test	    
//	    System.out.println("\nPrinting dados consolidados por Regiao:");
//	    List<RegiaoConsolidada> dadosConsolidados=listarDadoConsolidadoPorRegiao();
//	    for (Iterator iterator = dadosConsolidados.iterator(); iterator.hasNext();) {
//			RegiaoConsolidada regiaoConsolidada = (RegiaoConsolidada) iterator.next();
//			
//			System.out.println("\nSigla:"+regiaoConsolidada.getSigla());
//			System.out.println("compra média: \t"+regiaoConsolidada.getCompraMedia());
//			System.out.println("geração média: \t"+regiaoConsolidada.getGeracaoMedia());
//			System.out.println("preço médio: \t"+regiaoConsolidada.getPrecoMedioMedia());
//			
//		}
	    
    }
	
	
	/**
	 * List Dados consolidados por região usando como parametro a média de geração, compra e preço medio
	 * @return 
	 */
	public List<RegiaoConsolidada> listarDadoConsolidadoPorRegiao() {
		List<RegiaoConsolidada> dadosConsolidados = new ArrayList<>();

		List<Regiao> regioes = regiaoDataRepository.findAll();
		for (Regiao regiao : regioes) {
			RegiaoConsolidada dadoConsolidado = new RegiaoConsolidada();
			dadoConsolidado.setSigla(regiao.getSigla());

			double somaGeracao = 0;
			double somaCompra = 0;
			double somaPrecoMedio = 0;
			int contador = 0;

			List<Geracao> geracoes = regiao.getGeracoes();
			for (Geracao geracao : geracoes) {
				somaGeracao += geracao.getValor();
				contador++;
			}

			List<Compra> compras = regiao.getCompras();
			for (Compra compra : compras) {
				somaCompra += compra.getValor();
			}

			List<PrecoMedio> precosMedios = regiao.getPrecosMedios();
			for (PrecoMedio precoMedio : precosMedios) {
				somaPrecoMedio += precoMedio.getValor();
			}

			dadoConsolidado.setGeracaoMedia(somaGeracao / contador);
			dadoConsolidado.setCompraMedia(somaCompra / contador);
			dadoConsolidado.setPrecoMedioMedia(somaPrecoMedio / contador);

			dadosConsolidados.add(dadoConsolidado);
		}

		return dadosConsolidados;
	}


}