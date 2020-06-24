package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	
	private List<Food> food;
	private Map<Integer,Food> mappaCibi;
	private Graph<Food,DefaultWeightedEdge> grafo;
	private FoodDAO dao;
	
	public Model() {
		this.dao= new FoodDAO();
		this.mappaCibi= new HashMap<Integer, Food>();
		this.food= new ArrayList<Food>();
	}
	
	public void creaGrafo(int ctn) {
		this.grafo= new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		food= dao.getVertex(ctn,mappaCibi);
		Graphs.addAllVertices(grafo, food);
		
		for(Archi a : dao.getEdge(mappaCibi)) {
			Graphs.addEdgeWithVertices(grafo, a.getF1(), a.getF2(), a.getAvg());
		}
		
	}
	
	public List<Food> getVertex(){
		List<Food> result = new ArrayList<Food>();
		for(Food f: grafo.vertexSet()) {
			result.add(f);
		}
		Collections.sort(result);
		return result;
	}

	public List <Calorie> getVicini(Food f){
		List<Calorie> result = new ArrayList<Calorie>();
		List<Calorie> finale = new ArrayList<Calorie>();
		List<Food> vicini = Graphs.neighborListOf(grafo, f);
		for(Food f1: vicini) {
			Calorie c = new Calorie(f1, grafo.getEdgeWeight(grafo.getEdge(f, f1)));
			result.add(c);
		}
		Collections.sort(result);
		
		
		return result;
	}
	
	public String simula(Food partenza, int k) {
		Simulator sim= new Simulator(this.grafo, this);
		sim.setK(k);
		sim.init(partenza);
		sim.run();
		String messaggio= String.format("preparati %d cibi in %f in minuti \n", sim.getNumeroCibi(),sim.getTempoPreparazione());
		return messaggio;
	}
	
	
	
	
	
}
