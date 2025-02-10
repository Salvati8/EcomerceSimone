package com.betacom.bec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.bec.models.MessageID;
import com.betacom.bec.models.Messaggi;

public interface MessaggiRepository extends JpaRepository<Messaggi,MessageID>{

}