package com.betacom.bec.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.bec.dto.ProdottoDTO;
import com.betacom.bec.request.ProdottoReq;
import com.betacom.bec.response.ResponseBase;
import com.betacom.bec.response.ResponseList;
import com.betacom.bec.services.interfaces.ProdottoServices;

@RestController
@RequestMapping("/rest/prodotto")
public class ProdottoController {

	@Autowired
	private ProdottoServices proS;
	
	@Autowired
	private Logger log;
	
	@PostMapping("/create")
	public ResponseBase create(@RequestBody (required= true) ProdottoReq req) {
		log.debug("create: " + req);
		ResponseBase r= new ResponseBase();
		r.setRc(true);
		
		try {
			proS.create(req);
		}catch(Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
		
		
	}
	
	@PostMapping("/getById")
    public ResponseList<ProdottoDTO> getById(@RequestBody(required = true) Integer id) {
        log.debug("getById: " + id);
        ResponseList<ProdottoDTO> r = new ResponseList<ProdottoDTO>();
        r.setRc(true);
        
        try {
            ProdottoDTO prodottoDTO = proS.listById(id);
            r.setData(prodottoDTO);
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }
	
	@GetMapping("/listByCategoria/{categoria}")
    public ResponseList<ProdottoDTO> listByCategoria(@PathVariable String categoria) {
        log.debug("Lista prodotti per categoria: " + categoria);
        ResponseList<ProdottoDTO> r = new ResponseList<ProdottoDTO>();
        try {
            r.setDati(proS.listByCategoria(categoria));
            r.setRc(true);
        } catch (Exception e) {
            log.error("Errore nel recupero dei prodotti per categoria: ", e);
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

}
