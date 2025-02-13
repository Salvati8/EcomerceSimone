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

import com.betacom.bec.controller.RecensioneController;
import com.betacom.bec.dto.RecensioneDTO;
import com.betacom.bec.request.RecensioneReq;
import com.betacom.bec.response.ResponseBase;
import com.betacom.bec.response.ResponseList;

@SpringBootTest
@ActiveProfiles(value = "sviluppo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecensioneControllerTest {

    @Autowired
    RecensioneController recensioneController;

    @Autowired
    Logger log;

    @Test
    @Order(1)
    public void createRecensioneTest() throws Exception {
        RecensioneReq req = new RecensioneReq();
        req.setValutazione(5);
        req.setCommento("Ottimo prodotto");
        req.setUtente(null); // Simulazione utente (da sostituire con un utente valido)
        req.setProdotto(null); // Simulazione prodotto (da sostituire con un prodotto valido)

        ResponseBase r = recensioneController.create(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false); // Dipende dalla validità dei dati
        log.debug("Recensione creata: " + req.getCommento());
    }

    @Test
    @Order(2)
    public void updateRecensioneTest() throws Exception {
        RecensioneReq req = new RecensioneReq();
        req.setId(1); // Supponiamo che la recensione con ID 1 esista
        req.setValutazione(4);
        req.setCommento("Buon prodotto, ma con qualche difetto");

        ResponseBase r = recensioneController.update(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        log.debug("Recensione aggiornata: " + req.getCommento());
    }

    @Test
    @Order(3)
    public void listAllRecensioniTest() {
        ResponseList<RecensioneDTO> r = recensioneController.listAll();

        Assertions.assertThat(r.getRc()).isEqualTo(true);
        //Assertions.assertThat(r.getDati()).isNotEmpty();
    }

    @Test
    @Order(4)
    public void removeRecensioneTest() throws Exception {
        RecensioneReq req = new RecensioneReq();
        req.setId(1); // Supponiamo che la recensione con ID 1 esista

        ResponseBase r = recensioneController.remove(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        log.debug("Recensione eliminata con ID: " + req.getId());
    }

    @Test
    @Order(5)
    public void removeRecensioneErrorTest() throws Exception {
        RecensioneReq req = new RecensioneReq();
        req.setId(999); // ID che non esiste

        ResponseBase r = recensioneController.remove(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("no-recensione");
    }
}
