package com.betacom.bec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.bec.models.Carrello;

public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
	
    List<Carrello> findByUtenteId(int utenteId);
    

}