package com.betacom.bec.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.bec.models.Carrello;
import com.betacom.bec.models.Utente;

public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
	
    List<Carrello> findByUtenteId(int utenteId);

	Optional<Carrello> findByUtente(Utente utente);
    

}