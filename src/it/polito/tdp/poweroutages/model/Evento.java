package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {

	public enum TipoEvento {
		INIZIO_INTERRUZIONE,
		FINE_INTERRUZIONE;
	}
	
	private TipoEvento tipo;
	private PowerOutage interruzione;
	private LocalDateTime data;
	
	public Evento(TipoEvento tipo, PowerOutage interruzione, LocalDateTime data) {
		super();
		this.tipo = tipo;
		this.interruzione = interruzione;
		this.data = data;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public PowerOutage getInterruzione() {
		return interruzione;
	}

	public void setInterruzione(PowerOutage interruzione) {
		this.interruzione = interruzione;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Evento [tipo=" + tipo + ", interruzione=" + interruzione + ", data=" + data + "]";
	}

	@Override
	public int compareTo(Evento arg0) {
		return this.data.compareTo(arg0.data);
	}
	
	
	
}
