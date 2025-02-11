package com.betacom.bec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.bec.dto.CarrelloDTO;
import com.betacom.bec.models.Carrello;
import com.betacom.bec.response.ResponseBase;
import com.betacom.bec.response.ResponseList;
import com.betacom.bec.response.ResponseObject;
import com.betacom.bec.services.interfaces.CarrelloServices;

@RestController
@RequestMapping("/rest/carrello")
public class CarrelloController {
	
	@Autowired
	CarrelloServices carrelloS;
	
	@Autowired
	org.slf4j.Logger log;
	

	@GetMapping("/lista")
	public ResponseList<CarrelloDTO> getAllCarrelli(@RequestParam int idutente) {

	    ResponseList<CarrelloDTO> response = new ResponseList<CarrelloDTO>();
	    response.setRc(true);
	    try {
	    	response.setDati(carrelloS.ottieniCarrello(idutente));

	    } catch (Exception e) {
	        response.setMsg(e.getMessage());
	        response.setRc(false);
	    }
	    return response;
	}

	
	
	@PostMapping("/rimuovi")
	public ResponseBase rimuoviProdotto(@RequestParam int utenteId, int prodottoId,int quantitaDaRimuovere) {

	    ResponseBase response = new ResponseBase();
	    response.setRc(true);
	    try {
	        carrelloS.rimuoviProdotto(utenteId, prodottoId,quantitaDaRimuovere);
	    } catch (Exception e) {
	        response.setMsg(e.getMessage());
	        response.setRc(false);
	    }
	    return response;
	}
	
	@PostMapping("/aggiungi")
    public ResponseObject<Carrello> aggiungiProdotto(@RequestParam int utenteId, @RequestParam int prodottoId, @RequestParam int quantita) {
        log.debug("aggiungiProdotto: utenteId=" + utenteId + ", prodottoId=" + prodottoId + ", quantita=" + quantita);
        ResponseObject<Carrello> response = new ResponseObject<>();
        response.setRc(true);
        try {
            Carrello carrello = carrelloS.aggiungiProdottoAlCarrello(utenteId, prodottoId, quantita);
            log.debug("1");
            
            response.setDati(carrello);

        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }




}