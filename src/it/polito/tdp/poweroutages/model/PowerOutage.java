package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	private int id;
	private Nerc nerc;
	private LocalDateTime oraInizio;
	private LocalDateTime oraFine;
	
	public PowerOutage(int id, Nerc nerc, LocalDateTime oraInizio, LocalDateTime oraFine) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalDateTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalDateTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalDateTime oraFine) {
		this.oraFine = oraFine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
