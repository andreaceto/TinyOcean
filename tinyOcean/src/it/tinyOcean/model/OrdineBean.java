package it.tinyOcean.model;

import java.time.LocalDate;

public class OrdineBean {
	private long numOrdine;
	private String utente;
	private float costoTot;
	private LocalDate dataOrdine;
	private LocalDate dataSpedizione;
	private String indirizzoSped;
	private String metodoPagamento;
	
	public long getNumOrdine() {
		return numOrdine;
	}
	public void setNumOrdine(long numOrdine) {
		this.numOrdine = numOrdine;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public float getCostoTot() {
		return costoTot;
	}
	public void setCostoTot(float costoTot) {
		this.costoTot = costoTot;
	}
	public LocalDate getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(LocalDate dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	public LocalDate getDataSpedizione() {
		return dataSpedizione;
	}
	public void setDataSpedizione(LocalDate dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}
	public String getIndirizzoSped() {
		return indirizzoSped;
	}
	public void setIndirizzoSped(String indirizzoSped) {
		this.indirizzoSped = indirizzoSped;
	}
	public String getMetodoPagamento() {
		return metodoPagamento;
	}
	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

}
