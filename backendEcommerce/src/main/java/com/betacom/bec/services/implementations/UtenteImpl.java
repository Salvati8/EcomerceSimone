package com.betacom.bec.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.betacom.bec.dto.CarrelloDTO;
import com.betacom.bec.dto.ProdottoDTO;
import com.betacom.bec.dto.UtenteDTO;
import com.betacom.bec.models.Carrello;
import com.betacom.bec.models.Prodotto;
import com.betacom.bec.models.Utente;
import com.betacom.bec.repositories.CarrelloRepository;
import com.betacom.bec.repositories.UtenteRepository;
import com.betacom.bec.request.ProdottoReq;
import com.betacom.bec.request.UtenteReq;
import com.betacom.bec.services.interfaces.MessaggioServices;
import com.betacom.bec.services.interfaces.UtenteServices;
import com.betacom.bec.utils.Roles;

import static com.betacom.bec.utils.Utilities.buildOrdineDTO;
import static com.betacom.bec.utils.Utilities.buildRecensioneDTO;;
@Service
public class UtenteImpl implements UtenteServices{

	@Autowired
	Logger log;
	
	
	@Autowired
	UtenteRepository utR;
	
	@Autowired
	CarrelloRepository caR;
	
	@Autowired
	private MessaggioServices msgS;
	
	@Override
    public List<UtenteDTO> listAll() {
        List<Utente> utenti = utR.findAll();

        return utenti.stream().map(u -> new UtenteDTO(
                u.getId(),
                u.getNome(),
                u.getCognome(),
                u.getEmail(),
                u.getPsw(),
                u.getRuolo().toString(), 
                buildOrdineDTO(u.getOrdini()),
                buildRecensioneDTO(u.getRecensioni())
        )).collect(Collectors.toList());
    }
	
	@Override
	public void create(UtenteReq req) throws Exception {
		
		System.out.println("Create : " + req);
		
		Optional<Utente> c = utR.findByNome(req.getNome().trim());
		
		if(c.isPresent())
			throw new Exception(msgS.getMessaggio("find-utente"));
		
		if (req.getNome() == null)
			throw new Exception(msgS.getMessaggio("no-nome"));
		if (req.getCognome() == null)
			throw new Exception(msgS.getMessaggio("no-cognome"));
		if (req.getEmail() == null)
			throw new Exception(msgS.getMessaggio("no-email"));
		if (req.getPassword() == null)
			throw new Exception(msgS.getMessaggio("no-psw"));
		if (req.getRuolo() == null)
			throw new Exception(msgS.getMessaggio("no-ruolo"));
		if (req.getNumeroTelefono() == null)
			throw new Exception(msgS.getMessaggio("no-num-tel"));
		if (req.getIndirizzoDiSpedizione() == null)
			throw new Exception(msgS.getMessaggio("no-spedizione"));
		if (req.getIndirizzoDiFatturazione() == null)
			throw new Exception(msgS.getMessaggio("no-fatturazione"));

		
		Utente utente = new Utente();
		
		utente.setNome(req.getNome());
		utente.setCognome(req.getCognome());
		utente.setEmail(req.getEmail());
		utente.setPsw(req.getPassword());
		utente.setRuolo(Roles.valueOf(req.getRuolo()));
		utente.setNumeroTelefono(req.getNumeroTelefono());
		utente.setIndirizzoDiSpedizione(req.getIndirizzoDiSpedizione());
		utente.setIndirizzoDiFatturazione(req.getIndirizzoDiFatturazione());


      // Salva utente
		utR.save(utente);
		
	    // Creazione e associazione del carrello
	    Carrello carrello = new Carrello();
	    carrello.setQuantita(0);
	    carrello.setPrezzo(0.0);
	    carrello.setUtente(utente);
	    
	    
	    
	    caR.save(carrello);
		
	}

	
	
	@Override
	public void delete(String nomeUtente) throws Exception {
	    System.out.println("Delete : " + nomeUtente);
	    
	    Optional<Utente> utenteOpt = utR.findByNome(nomeUtente.trim());
	    
	    if (!utenteOpt.isPresent()) {
	        throw new Exception(msgS.getMessaggio("utente-non-trovato"));
	    }
	    
	    Utente utente = utenteOpt.get();
	    
	    // Trova il carrello associato
	    Optional<Carrello> carrelloOpt = caR.findByUtente(utente);
	    
	    // Se il carrello esiste, lo elimina
	    carrelloOpt.ifPresent(carrello -> caR.delete(carrello));
	    
	    // Elimina l'utente
	    utR.delete(utente);
	    
	    System.out.println("Utente e carrello eliminati con successo");
	}

	@Override
    public void update(String nomeUtente, UtenteReq req, String ruoloRichiedente) throws Exception {
        System.out.println("Update : " + nomeUtente);
        
        Optional<Utente> utenteOpt = utR.findByNome(nomeUtente.trim());
        
        if (!utenteOpt.isPresent()) {
            throw new Exception(msgS.getMessaggio("utente-non-trovato"));
        }
        
        Utente utente = utenteOpt.get();
        
        // Aggiorna i campi modificabili
        utente.setNome(req.getNome());
        utente.setCognome(req.getCognome());
        utente.setEmail(req.getEmail());
        utente.setPsw(req.getPassword());
        utente.setNumeroTelefono(req.getNumeroTelefono());
        utente.setIndirizzoDiSpedizione(req.getIndirizzoDiSpedizione());
        utente.setIndirizzoDiFatturazione(req.getIndirizzoDiFatturazione());
        
        // Controllo sul ruolo: solo un admin può modificarlo
        if (Roles.ADMIN.toString().equals(ruoloRichiedente)) {
            utente.setRuolo(Roles.valueOf(req.getRuolo()));
        }
        
        utR.save(utente);
        System.out.println("Utente aggiornato con successo");
    }
	
	
}