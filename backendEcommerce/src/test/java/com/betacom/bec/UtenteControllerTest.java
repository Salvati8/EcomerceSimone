package com.betacom.bec;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.betacom.bec.controller.UtenteController;
import com.betacom.bec.dto.UtenteDTO;
import com.betacom.bec.request.UtenteReq;
import com.betacom.bec.response.ResponseBase;
import com.betacom.bec.response.ResponseList;

@SpringBootTest
@ActiveProfiles(value = "sviluppo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtenteControllerTest {

    @Autowired
    UtenteController utenteController;

    @Autowired
    Logger log;

    @Test
    @Order(1)
    public void createUtenteTest() throws Exception {
        UtenteReq req = new UtenteReq();
        req.setNome("Mario");
        req.setCognome("Rossi");
        req.setEmail("mario.rossi@example.com");
        req.setPassword("password123");
        req.setRuolo("USER");
        req.setNumeroTelefono("1234567890");
        req.setIndirizzoDiSpedizione("Via Roma, 1");
        req.setIndirizzoDiFatturazione("Via Milano, 2");

        ResponseBase r = utenteController.create(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        log.debug("Utente creato: " + req.getEmail());
    }

    @Test
    @Order(2)
    public void listUtentiTest() {
        ResponseList<UtenteDTO> r = utenteController.list();

        Assertions.assertThat(r.getRc()).isEqualTo(true);
        Assertions.assertThat(r.getDati()).isNotEmpty();
    }

    @Test
    @Order(3)
    public void updateUtenteTest() throws Exception {
        UtenteReq req = new UtenteReq();
        req.setId(1); // Supponiamo che l'utente con ID 1 esista
        req.setCognome("Verdi");
        req.setEmail("mario.verdi@example.com");

        ResponseBase r = utenteController.update(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        log.debug("Utente aggiornato con email: " + req.getEmail());
    }

    @Test
    @Order(4)
    public void removeUtenteTest() throws Exception {
        UtenteReq req = new UtenteReq();
        req.setId(1); // Supponiamo che l'utente con ID 1 esista

        ResponseBase r = utenteController.remove(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        log.debug("Utente eliminato con ID: " + req.getId());
    }

    @Test
    @Order(5)
    public void removeUtenteNotFoundTest() throws Exception {
        UtenteReq req = new UtenteReq();
        req.setId(9999); // ID inesistente

        ResponseBase r = utenteController.remove(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("Username inesistente");
    }
}

