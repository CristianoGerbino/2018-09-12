package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;
import it.polito.tdp.poweroutages.model.Evento.TipoEvento;

public class Simulatore {
	
	private Queue<Evento> queue;
	
	//Stato del mondo
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	private List<PowerOutage> listaOutage;
	private Map<Integer, Nerc> idMapNerc;
	
	//Variabili interne
	private PowerOutagesDAO dao;
	
	
	private Map<Nerc, List<Donazione>> mappaDonazioni;
	
	
	public Simulatore() {
		dao = new PowerOutagesDAO();
		
	}


	public void init(Graph<Nerc, DefaultWeightedEdge> grafo, Map<Integer, Nerc> idMapNerc) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.grafo = grafo;
		
		listaOutage = new ArrayList<>();
		idMapNerc = new HashMap<>();
		this.idMapNerc = idMapNerc;
		
		listaOutage = dao.loadAllOutages(idMapNerc);
		
		//Creaiamo la mappa delle donazioni
		mappaDonazioni = new HashMap<>();
		for (Nerc n : idMapNerc.values()) {
			List<Donazione> list = new ArrayList<>();
			mappaDonazioni.put(n, list);
		}
		
		//Scheduliamo gli eventi nella coda
		for (PowerOutage po : listaOutage) {
			Evento inizio = new Evento(TipoEvento.INIZIO_INTERRUZIONE, po, po.getOraInizio());
			Evento fine = new Evento(TipoEvento.FINE_INTERRUZIONE, po, po.getOraFine());
			queue.add(inizio);
			queue.add(fine);
		}
		
		
	}
	
	public void simula (int K) {
		
		while (!queue.isEmpty()) {
			
			Evento ev = queue.poll();
			
			switch (ev.getTipo()) {
			
				case INIZIO_INTERRUZIONE:
					Nerc n = ev.getInterruzione().getNerc();
					Nerc donatore = trovaDonatore(n, K, ev.getData());
					
					
					break;
					
				case FINE_INTERRUZIONE:
					break;
			}
		}
	}


	private Nerc trovaDonatore(Nerc n, int K, LocalDateTime data) {
		
		List<Donazione> donati = mappaDonazioni.get(n);
		
		//Se non ci sono ancora state donazioni
		if (donati.isEmpty()) {
			Nerc donatore = trovaDonatoreVicinoMigliore (n);
			return donatore;
		}
		
		
		//Scorriamo la lista dei donatori e troviamo i papabili donatori
		List<Nerc> papabili = new ArrayList<>();
		for (Donazione d : donati) {
			if (Duration.between(data, d.getFineDonazione()).compareTo(Duration.ofDays(K*30)) <= 0 && isFree(d.getDonato())) {
				papabili.add(d.getDonato());
			}
		}
		
		//Se non ci sono papabili donatori
		if (papabili.isEmpty()) {
			Nerc donatore1 = this.trovaDonatoreVicinoMigliore(n);
			return donatore1;
		}
		
		//Se ci sono più papabili
		if (papabili.size() >1 ) {
			double peso = Double.MAX_VALUE;
			Nerc best = null;
			
			for (Nerc vicino : papabili) {
				double weight = grafo.getEdgeWeight(grafo.getEdge(n, vicino));
				if (weight < peso) {
					peso = weight;
					best = vicino;
				}
			}
			return best;
		}
		
		return papabili.get(0);
	}


	private Nerc trovaDonatoreVicinoMigliore(Nerc n) {
		
		double peso = Double.MAX_VALUE;
		Nerc best = null;
		
		for (Nerc vicino : Graphs.neighborListOf(grafo, n)) {
			double weight = grafo.getEdgeWeight(grafo.getEdge(n, vicino));
			if (weight < peso) {
				peso = weight;
				best = vicino;
			}
		}
		return best;
	}
	
	
}
