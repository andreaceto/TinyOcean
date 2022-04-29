package it.tinyOcean.model;

import java.time.LocalDate;

public class MetodoPagamentoBean {
	private long numCarta;
	private int tipo;
	private LocalDate scadenza;
	private String titolare;
	private String indirizzoFatt;
	private boolean predefinito;
	
	public long getNumCarta() {
		return numCarta;
	}
	public void setNumCarta(long numCarta) {
		this.numCarta = numCarta;
	}
	public int getRawTipo() {
		return tipo;
	}
	public String getTipo() {
		return (tipo == 0)? "carta di credito":"conto bancario" ;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public LocalDate getScadenza() {
		return scadenza;
	}
	public void setScadenza(LocalDate scadenza) {
		this.scadenza = scadenza;
	}
	public String getTitolare() {
		return titolare;
	}
	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}
	public String getIndirizzoFatt() {
		return indirizzoFatt;
	}
	public void setIndirizzoFatt(String indirizzoFatt) {
		this.indirizzoFatt = indirizzoFatt;
	}
	public boolean isPredefinito() {
		return predefinito;
	}
	public void setPredefinito(boolean predefinito) {
		this.predefinito = predefinito;
	}
	
	
}
