package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO dao;
	Graph<Artist, DefaultWeightedEdge> grafo;
	Map<Integer, Artist> mappaArtisti;
	List<Arco> listaInfoArchi;
	
	List<Artist> percorsoMax;
	double pesoPercorsoMax;

	public List<String> getAllDistinctRoles() {
		
		dao = new ArtsmiaDAO();
		
		return dao.getAllDistinctRoles();
	}

	public void creaGrafo(String ruolo) {
		
		mappaArtisti = this.dao.getAllArtistsByRole(ruolo);
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, mappaArtisti.values());
		
		listaInfoArchi = this.dao.getAllArchiByRole(ruolo);
		
		for(Arco a : listaInfoArchi) {
			
			Graphs.addEdge(grafo, mappaArtisti.get(a.getId1()), mappaArtisti.get(a.getId2()), a.getPeso());
		}
		
		System.out.println(grafo.vertexSet().size()+" vertici, "+grafo.edgeSet().size()+" archi");
	}

	public Map<Integer, Artist> getMappaArtisti() {
		return mappaArtisti;
	}

	public List<Arco> getListaInfoArchi() {
		return listaInfoArchi;
	}

	public List<Artist> calcolaPercorso(int id) {
		
		Artist artista = mappaArtisti.get(id);
		
		List<Artist> parziale = new ArrayList<>();
		parziale.add(artista);
		
		List<Artist> listaDisponibili = new LinkedList<>();
		for(Artist a : mappaArtisti.values()) {
			
			listaDisponibili.add(a);
		}
		listaDisponibili.remove(artista);
		
		percorsoMax = new ArrayList<>();
		pesoPercorsoMax = 0.0;
		
		for(Artist a : Graphs.neighborListOf(grafo, artista)) {
			
			double peso = grafo.getEdgeWeight(grafo.getEdge(artista, a));
			
			this.ricorsione(peso, parziale, listaDisponibili, a);
		}
		
		return percorsoMax;
	}

	private void ricorsione(double peso, List<Artist> parziale, List<Artist> listaDisponibili, Artist artista) {
		
		parziale.add(artista);
		listaDisponibili.remove(artista);
		
		boolean stop = true;
		
		for(Artist a : Graphs.neighborListOf(grafo, artista)) {
			
			if( listaDisponibili.contains(a) && grafo.getEdgeWeight(grafo.getEdge(artista, a))==peso ) {
				
				this.ricorsione(peso, parziale, listaDisponibili, a);
				stop = false;
			}
		}
		
		if(stop) {
			if(parziale.size()>percorsoMax.size()) {
				
				percorsoMax.clear();
				percorsoMax.addAll(parziale);
				
				pesoPercorsoMax = peso;
			}
		}
	}

	public double getPesoPercorsoMax() {
		return pesoPercorsoMax;
	}

	public Graph<Artist, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	
}
