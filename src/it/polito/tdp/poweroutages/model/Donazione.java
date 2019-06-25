package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Donazione {
	private Nerc donato;
	private LocalDateTime inizioDonazione;
	private LocalDateTime fineDonazione;
	
	public Donazione(Nerc donato, LocalDateTime inizioDonazione, LocalDateTime fineDonazione) {
		super();
		this.donato = donato;
		this.inizioDonazione = inizioDonazione;
		this.fineDonazione = fineDonazione;
	}

	public Nerc getDonato() {
		return donato;
	}

	public void setDonato(Nerc donato) {
		this.donato = donato;
	}

	public LocalDateTime getInizioDonazione() {
		return inizioDonazione;
	}

	public void setInizioDonazione(LocalDateTime inizioDonazione) {
		this.inizioDonazione = inizioDonazione;
	}

	public LocalDateTime getFineDonazione() {
		return fineDonazione;
	}

	public void setFineDonazione(LocalDateTime fineDonazione) {
		this.fineDonazione = fineDonazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((donato == null) ? 0 : donato.hashCode());
		result = prime * result + ((fineDonazione == null) ? 0 : fineDonazione.hashCode());
		result = prime * result + ((inizioDonazione == null) ? 0 : inizioDonazione.hashCode());
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
		Donazione other = (Donazione) obj;
		if (donato == null) {
			if (other.donato != null)
				return false;
		} else if (!donato.equals(other.donato))
			return false;
		if (fineDonazione == null) {
			if (other.fineDonazione != null)
				return false;
		} else if (!fineDonazione.equals(other.fineDonazione))
			return false;
		if (inizioDonazione == null) {
			if (other.inizioDonazione != null)
				return false;
		} else if (!inizioDonazione.equals(other.inizioDonazione))
			return false;
		return true;
	}
	
	
	
	
}
