package com.betacom.bec.services.implementations;

import static com.betacom.bec.utils.Utilities.buildCarrelloProdottoDTO;
import static com.betacom.bec.utils.Utilities.convertStringToDate;

import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.bec.dto.CarrelloDTO;
import com.betacom.bec.dto.OrdineDTO;
import com.betacom.bec.models.Carrello;
import com.betacom.bec.models.Ordine;
import com.betacom.bec.repositories.CarrelloRepository;
import com.betacom.bec.repositories.OrdineRepository;
import com.betacom.bec.request.OrdineReq;
import com.betacom.bec.services.interfaces.MessaggioServices;
import com.betacom.bec.services.interfaces.OrdineServices;


@Service
public class OrdineImpl implements OrdineServices{
	
	@Autowired
	OrdineRepository orR;
	
	@Autowired
	CarrelloRepository carR;
	
	
	@Autowired
	private MessaggioServices msgS;
	
	@Autowired
	Logger log;
	
	@Override
	public void create(OrdineReq req) throws Exception {
	    if (req.getIndirizzoDiSpedizione() == null)
	        throw new Exception(msgS.getMessaggio("no-spedizione"));
	    if (req.getCap() == null)
	        throw new Exception(msgS.getMessaggio("no-cap"));
	    if (req.getCitta() == null)
	        throw new Exception(msgS.getMessaggio("no-citta"));

	    Ordine ordine = new Ordine();
	    ordine.setIndirizzoDiSpedizione(req.getIndirizzoDiSpedizione());
	    ordine.setCap(req.getCap());
	    ordine.setCitta(req.getCitta());
	    ordine.setDataOrdine(req.getDataOrdine() == null || req.getDataOrdine().isEmpty()
	            ? new Date() 
	            : convertStringToDate(req.getDataOrdine()));

	    if (req.getCarrelloId() != null) {
	        Carrello carrello = carR.findById(req.getCarrelloId())
	                .orElseThrow(() -> new Exception("Carrello non trovato"));
	        ordine.setCarrello(carrello);
	    }

	    orR.save(ordine);
	}

	
	@Override
	public List<OrdineDTO> listByUtente(Integer idUtente) {
	    Optional<Carrello> carrelloOpt = carR.findByUtenteId(idUtente);

	    if (carrelloOpt.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<Ordine> ordini = orR.findByCarrelloId(carrelloOpt.get().getId());

	    return ordini.stream().map(o -> new OrdineDTO(
	            o.getId(),
	            o.getIndirizzoDiSpedizione(),
	            o.getCap(),
	            o.getCitta(),
	            o.getDataOrdine(),
	            new CarrelloDTO(
	                    o.getCarrello().getId(),
	                    o.getCarrello().getQuantita(),
	                    o.getCarrello().getPrezzo(),
	                    buildCarrelloProdottoDTO(o.getCarrello().getCarrelloProdotti())
	            )
	    )).collect(Collectors.toList());
	}



}