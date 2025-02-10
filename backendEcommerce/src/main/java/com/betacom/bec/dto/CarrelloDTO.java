package com.betacom.bec.dto;

import java.sql.Timestamp;



public class CarrelloDTO {

	private Integer id;
    private Integer idProdotto;
    private Integer quantita;
    private Double prezzo;
    private UtenteDTO utente;
    
	public CarrelloDTO(Integer id, Integer idProdotto, Integer quantita, Double prezzo, UtenteDTO utente) {
		super();
		this.id = id;
		this.idProdotto = idProdotto;
		this.quantita = quantita;
		this.prezzo = prezzo;
		this.utente = utente;
	}

	public CarrelloDTO() {
		super();
	}

	public CarrelloDTO(Integer id) {
		super();
		this.id = id;
		
	}

	@Override
	public String toString() {
		return "CarrelloDTO [id=" + id + ", dataCreazione=" +  ", idProdotto="
				+ idProdotto + ", quantita=" + quantita + ", prezzo=" + prezzo + ", utente=" + utente + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(Integer idProdotto) {
		this.idProdotto = idProdotto;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}
    
	
    
    

}
