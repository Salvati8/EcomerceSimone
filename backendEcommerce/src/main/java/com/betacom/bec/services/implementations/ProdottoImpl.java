package com.betacom.bec.services.implementations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.bec.dto.ProdottoDTO;
import com.betacom.bec.models.Prodotto;
import com.betacom.bec.repositories.ProdottoRepository;
import com.betacom.bec.request.ProdottoReq;
import com.betacom.bec.services.interfaces.ProdottoServices;

@Service
public class ProdottoImpl implements ProdottoServices{

	@Autowired
	private ProdottoRepository proR;
	
	@Autowired
	private Logger log;
	
	@Override
	public void create(ProdottoReq req) throws Exception {
		
		System.out.println("Create : " + req);
		
		Optional<Prodotto> c = proR.findByNome(req.getNome().trim());
		
		if(c.isPresent())
			throw new Exception("Prodotto già presente");
		
		Prodotto prodotto = new Prodotto();
		
		prodotto.setMarca(req.getMarca());
		prodotto.setNome(req.getNome());
		prodotto.setCategoria(req.getCategoria());
		prodotto.setDescrizione(req.getDescrizione());
		prodotto.setPrezzo(req.getPrezzo());
		prodotto.setQuantitaDisponibile(req.getQuantitaDisponibile());
		prodotto.setUrlImg(req.getUrlImg());
		prodotto.setSize(req.getSize());
		prodotto.setColore(req.getColore());
		
      // Salva la Prodotto
		proR.save(prodotto);
		
	}
	 
	@Override
    public ProdottoDTO listById(Integer id) {
        Prodotto prodotto = proR.findById(id).orElse(null);
        return prodotto != null ? new ProdottoDTO(prodotto) : null;
    }
	
	@Override
	public List<ProdottoDTO> listByCategoria(String categoria) {
	    List<Prodotto> prodotti = proR.findByCategoria(categoria);
	    return prodotti.stream().map(ProdottoDTO::new).collect(Collectors.toList());
	}
}
