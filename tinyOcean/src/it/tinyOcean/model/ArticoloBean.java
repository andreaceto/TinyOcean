package it.tinyOcean.model;

import java.io.Serializable;

public class ArticoloBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String produttore;
	private String nome;
	private String descrizione;
	private float prezzo;
	private int stock;
	private float peso;
	private float altezza;
	private float larghezza;
	private float lunghezza;
	private int saldo;
	
	
	
	public ArticoloBean() {
		super();
		id = -1;
		produttore = "";
		nome = "";
		descrizione = "";
		prezzo = -1;
		stock = -1;
		peso = -1;
		altezza = -1;
		larghezza = -1;
		lunghezza = -1;
		saldo = -1;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduttore() {
		return produttore;
	}
	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public float getAltezza() {
		return altezza;
	}
	public void setAltezza(float altezza) {
		this.altezza = altezza;
	}
	public float getLarghezza() {
		return larghezza;
	}
	public void setLarghezza(float larghezza) {
		this.larghezza = larghezza;
	}
	public float getLunghezza() {
		return lunghezza;
	}
	public void setLunghezza(float lunghezza) {
		this.lunghezza = lunghezza;
	}
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", produttore=" + produttore + ", nome=" + nome + ", descrizione="
				+ descrizione + ", prezzo=" + prezzo + ", stock=" + stock + ", peso=" + peso + ", altezza=" + altezza
				+ ", larghezza=" + larghezza + ", lunghezza=" + lunghezza + ", saldo=" + saldo + "]";
	}
	
	
}
