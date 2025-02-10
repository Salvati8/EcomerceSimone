package com.betacom.bec.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.betacom.bec.dto.OrdineDTO;
import com.betacom.bec.dto.RecensioneDTO;
import com.betacom.bec.models.Ordine;
import com.betacom.bec.models.Recensione;



public class Utilities {

	private final static String PATTERN_DATE="dd/MM/yyyy";
	
	public static Date convertStringToDate(String dataString) throws ParseException {
		
		SimpleDateFormat formatter= new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
		return formatter.parse(dataString);
	}
	
     public static String convertDateToString(Date data){
		DateFormat formatter= new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
		return formatter.format(data);
	}
	
     public static List<OrdineDTO> buildOrdineDTO(List<Ordine> a){
 		return a.stream()
 				.map(o-> new OrdineDTO(o.getId(), o.getPrezzoTotale(), o.getIndirizzoDiSpedizione(),o.getCap(),o.getDataOrdine(),o.getQuantità(),o.getStato()))
 				.collect(Collectors.toList());
 	}
     
     public static List<RecensioneDTO> buildRecensioneDTO(List<Recensione> a){
  		return a.stream()
  				.map(r-> new RecensioneDTO(r.getId(),r.getValutazione(),r.getCommento(),r.getDataRecensione()))
  				.collect(Collectors.toList());
  	}
	
}
