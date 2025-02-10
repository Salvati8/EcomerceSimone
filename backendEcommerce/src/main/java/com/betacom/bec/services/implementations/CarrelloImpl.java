package com.betacom.bec.services.implementations;
import java.util.List;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.bec.models.Carrello;
import com.betacom.bec.models.Prodotto;
import com.betacom.bec.models.Utente;
import com.betacom.bec.repositories.CarrelloRepository;
import com.betacom.bec.repositories.ProdottoRepository;
import com.betacom.bec.repositories.UtenteRepository;
import com.betacom.bec.services.interfaces.CarrelloServices;

@Service
public class CarrelloImpl implements CarrelloServices{

	@Autowired
	CarrelloRepository carR;
	
	@Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private UtenteRepository utenteRepository;
	
	@Autowired
	Logger log;
	
	@Override
	public Carrello aggiungiProdotto(int utenteId, int prodottoId, int quantita) {
	    // Trova l'utente
	    Utente utente = utenteRepository.findById(utenteId)
	            .orElseThrow(() -> new RuntimeException("Utente non trovato"));
	    
	    // Trova il prodotto
	    Prodotto prodotto = prodottoRepository.findById(prodottoId)
	            .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

	    // Verifica che la quantità sia valida
	    if (quantita <= 0) {
	        throw new RuntimeException("La quantità deve essere maggiore di zero.");
	    }

	    // Crea il nuovo carrello
	    Carrello nuovoCarrello = new Carrello();
	    nuovoCarrello.setUtente(utente);
	    nuovoCarrello.setProdotto(prodotto);
	    nuovoCarrello.setQuantita(quantita);
	    
	    // Imposta il prezzo direttamente dal prodotto
	    nuovoCarrello.setPrezzo(prodotto.getPrezzo() * quantita);

	    // Salva il nuovo carrello
	    return carR.save(nuovoCarrello);
	}



	@Override
	public Carrello aggiornaPrezziCarrello(int utenteId) {
	    List<Carrello> carrelloUtente = carR.findByUtenteId(utenteId);
	    
	    double totaleCarrello = 0;
	    
	    for (Carrello carrello : carrelloUtente) {
	        double prezzoTotaleProdotto = carrello.getProdotto().getPrezzo() * carrello.getQuantita();
	        carrello.setPrezzo(prezzoTotaleProdotto);
	        totaleCarrello += prezzoTotaleProdotto; // Somma il prezzo totale
	    }

	    // Aggiorna e restituisci il carrello con i prezzi aggiornati
	    carR.saveAll(carrelloUtente);
	    return carrelloUtente.isEmpty() ? null : carrelloUtente.get(0);  // Restituisci il primo carrello (o gestisci come preferisci)
	}

	@Override
	public double calcolaTotaleCarrello(int utenteId) {
	    List<Carrello> carrelloUtente = carR.findByUtenteId(utenteId);
	    
	    double totaleCarrello = 0;
	    for (Carrello carrello : carrelloUtente) {
	        totaleCarrello += carrello.getPrezzo();
	    }

	    return totaleCarrello;
	}
	
	

	@Override
    public void rimuoviProdotto(int carrelloId) {
    	carR.deleteById(carrelloId);
    }

	@Override
	public Carrello aggiornaQuantita(int carrelloId, int quantita) {
	    // Trova l'elemento nel carrello
	    Carrello carrello = carR.findById(carrelloId)
	            .orElseThrow(() -> new RuntimeException("Elemento nel carrello non trovato"));

	    // Trova il prodotto associato al carrello
	    Prodotto prodotto = carrello.getProdotto();

	    // Verifica che ci sia sufficiente quantità del prodotto
	    if (prodotto.getQuantitaDisponibile() < quantita) {
	        throw new RuntimeException("Quantità richiesta non disponibile");
	    }

	    // Aggiorna la quantità del prodotto nel carrello
	    carrello.setQuantita(quantita);
	    carR.save(carrello); // Salva l'aggiornamento del carrello

	    // Aggiorna la quantità del prodotto nel database
	    prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() - quantita);
	    prodottoRepository.save(prodotto); // Salva l'aggiornamento del prodotto

	    return carrello;
	}


    @Override
    public List<Carrello> ottieniCarrello(int utenteId) {
        return carR.findByUtenteId(utenteId);
    }
	
	
	
	
}