package com.betacom.bec.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.bec.models.Recensione;
import com.betacom.bec.repositories.RecensioneRepository;
import com.betacom.bec.request.RecensioneReq;
import com.betacom.bec.services.interfaces.MessaggioServices;
import com.betacom.bec.services.interfaces.RecensioneServices;

import static com.betacom.bec.utils.Utilities.convertStringToDate;

@Service
public class RecensioneImpl implements RecensioneServices{

	@Autowired
	RecensioneRepository recR;
	
	
	@Autowired
	private MessaggioServices msgS;
	
	@Autowired
	Logger log;
	
	@Override
	public void create(RecensioneReq req) throws Exception {
		
		System.out.println("Create : " + req);
		
		Optional<Recensione> c = recR.findById(req.getId());
		
		
		if(c.isPresent())
			throw new Exception(msgS.getMessaggio("find-recensione"));
		
		if (req.getValutazione() == null)
			throw new Exception(msgS.getMessaggio("no-valutazione"));
		if (req.getCommento() == null)
			throw new Exception(msgS.getMessaggio("no-commento"));
		
		
		Recensione rec = new Recensione();
		
		
		rec.setValutazione(req.getValutazione());
		rec.setCommento(req.getCommento());
		rec.setDataRecensione(convertStringToDate(req.getDataRecensione()));
		

      // Salva il prodotto
		recR.save(rec);
		
	}
	
	@Override
	public void update(RecensioneReq req) throws Exception {
	    log.debug("Update: " + req);
	    
	    // Controllo se già esiste una recensione con lo stesso ID
	    Optional<Recensione> optRecensione = recR.findById(req.getId());
	    if (optRecensione.isEmpty()) {
	        throw new Exception(msgS.getMessaggio("no-recensione"));
	    }
	    
	    Recensione r = optRecensione.get();
	    r.setValutazione(req.getValutazione());
	    r.setCommento(req.getCommento());
	    r.setDataRecensione(convertStringToDate(req.getDataRecensione()));
	    
	    recR.save(r);
	}

	@Override
	public List<Recensione> listAllRecensioni() throws Exception {
	    return recR.findAll();
	}
	
	@Override
	public void removeRecensione(RecensioneReq req) throws Exception {
	    Optional<Recensione> rec = recR.findById(req.getId());
	    if (rec.isEmpty()) {
	        throw new Exception(msgS.getMessaggio("no-recensione"));
	    }
	    
	    recR.delete(rec.get());
	} 
}
