package com.betacom.bec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping("/aggiornaQuantita")
	public ResponseBase aggiornaQuantita(@RequestParam int carrelloId, @RequestParam int quantita) {
	    log.debug("aggiornaQuantita: carrelloId=" + carrelloId + ", quantita=" + quantita);
	    ResponseBase response = new ResponseBase();
	    response.setRc(true);

	    try {
	        // Chiama il servizio per aggiornare la quantità
	        carrelloS.aggiornaQuantita(carrelloId, quantita);
	    } catch (Exception e) {
	        response.setMsg(e.getMessage());
	        response.setRc(false);
	    }

	    return response;
	}


	@GetMapping("/lista")
	public ResponseList<Carrello> getAllCarrelli(@RequestParam int idutente) {
	    log.debug("getAllCarrelli");
	    ResponseList<Carrello> response = new ResponseList<>();
	    response.setRc(true);
	    try {
	        List<Carrello> carrelli = carrelloS.ottieniCarrello(idutente); 
	        response.setDati(carrelli);
	    } catch (Exception e) {
	        response.setMsg(e.getMessage());
	        response.setRc(false);
	    }
	    return response;
	}
	
	
	@PostMapping("/rimuovi")
	public ResponseBase rimuoviProdotto(@RequestParam int carrelloId) {
	    log.debug("rimuoviProdotto: carrelloId=" + carrelloId);
	    ResponseBase response = new ResponseBase();
	    response.setRc(true);
	    try {
	        carrelloS.rimuoviProdotto(carrelloId);
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
            Carrello carrello = carrelloS.aggiungiProdotto(utenteId, prodottoId, quantita);
            response.setDati(carrello);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }

    @PostMapping("/aggiornaPrezzi")
    public ResponseBase aggiornaPrezziCarrello(@RequestParam int utenteId) {
        log.debug("aggiornaPrezziCarrello: utenteId=" + utenteId);
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            carrelloS.aggiornaPrezziCarrello(utenteId);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }

    @GetMapping("/totale")
    public ResponseObject<Double> calcolaTotaleCarrello(@RequestParam int utenteId) {
        log.debug("calcolaTotaleCarrello: utenteId=" + utenteId);
        ResponseObject<Double> response = new ResponseObject<>();
        response.setRc(true);
        try {
            double totale = carrelloS.calcolaTotaleCarrello(utenteId);
            response.setDati(totale);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }



}