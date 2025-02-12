package com.betacom.bec.dto;

import java.sql.Timestamp;
import java.util.Date;

public class RecensioneDTO {

	    private Integer id;
	    private Integer valutazione;
	    private String commento;
	    private Date dataRecensione;
	    
		public RecensioneDTO(Integer id, Integer valutazione, String commento,
				Date dataRecensione) {
			super();
			this.id = id;
			this.valutazione = valutazione;
			this.commento = commento;
			this.dataRecensione = dataRecensione;
		}

		public RecensioneDTO() {
			super();
		}

		@Override
		public String toString() {
			return "RecensioneDTO [id=" + id + ", valutazione=" + valutazione + ", commento=" + commento + ", dataRecensione=" + dataRecensione
					+ "]";
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getValutazione() {
			return valutazione;
		}

		public void setValutazione(Integer valutazione) {
			this.valutazione = valutazione;
		}

		public String getCommento() {
			return commento;
		}

		public void setCommento(String commento) {
			this.commento = commento;
		}

		public Date getDataRecensione() {
			return dataRecensione;
		}

		public void setDataRecensione(Date dataRecensione) {
			this.dataRecensione = dataRecensione;
		}

		

}
