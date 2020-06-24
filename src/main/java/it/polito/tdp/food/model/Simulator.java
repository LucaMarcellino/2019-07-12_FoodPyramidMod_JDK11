package it.polito.tdp.food.model;

import java.lang.invoke.SwitchPoint;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulator {
	
	//Modello del mondo 
	private List<Stazione> stazioni;
	private List<Food> cibi;
	private Graph<Food,DefaultWeightedEdge> grafo;
	private Model model;
	
	//parametri simulazione
	private int k=5;
	
	
	//risultati calcolati
	private Double tempoPreparazione;
	private int numeroCibi;
	
	
	//coda eventi
	private PriorityQueue<Event> queue;
	
	
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}

	public Simulator(Graph<Food,DefaultWeightedEdge> grafo, Model model) {
		this.grafo=grafo;
		this.model=model;
	}
	
	public void init(Food partenza) {
		this.cibi= new ArrayList<Food>(this.grafo.vertexSet());
		this.stazioni= new ArrayList<Stazione>();
		for(Food cibo: cibi)
			cibo.setPreparazione(StatoPreparazione.DA_PREPARARE);
		
		for( int i =0; i<k;i++)
			this.stazioni.add(new Stazione(true, null));
		this.tempoPreparazione=0.0;
		this.queue= new PriorityQueue<Event>();
		List<Calorie> vicini = model.getVicini(partenza);
		this.numeroCibi=0;
		
		for(int i=0; i<k && i<vicini.size();i++) {
			this.stazioni.get(i).setLibera(false);
			this.stazioni.get(i).setFood(vicini.get(i).getF());
			
			Event e = new  Event(vicini.get(i).getCalorie(), stazioni.get(i), vicini.get(i).getF(), EventType.FINE_PREPARAZIONE);
			queue.add(e);
		}
		
		
		
		
	}

	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e );
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		
		case INIZIO_PREPARAZIONE:
			List<Calorie> vicini1= model.getVicini(e.getFood());
			Calorie prossimo =null;
			for(Calorie vicino: vicini1) {
				if(vicino.getF().getPreparazione() == StatoPreparazione.DA_PREPARARE) {
					prossimo=vicino;
				break;
				}
				
			}
			if(prossimo != null) {
				prossimo.getF().setPreparazione(StatoPreparazione.IN_CORSO);
				e.getStazione().setLibera(false);
				e.getStazione().setFood(prossimo.getF());
			}
			Event e2 = new Event(e.getTime()+prossimo.getCalorie(), e.getStazione(), prossimo.getF(), EventType.FINE_PREPARAZIONE);
			queue.add(e2);
			
			break;
		case FINE_PREPARAZIONE:
				numeroCibi++;
				e.getStazione().setLibera(true);
				e.getFood().setPreparazione(StatoPreparazione.PREPARATO);
				tempoPreparazione= e.getTime();
			
				Event e3 = new Event(e.getTime(), e.getStazione(), e.getFood(), EventType.INIZIO_PREPARAZIONE);
				this.queue.add(e3);
			break;
		}
		
		
		
	}

	public int getNumeroCibi() {
		return numeroCibi;
	}

	public void setNumeroCibi(int numeroCibi) {
		this.numeroCibi = numeroCibi;
	}

	public void setTempoPreparazione(Double tempoPreparazione) {
		this.tempoPreparazione = tempoPreparazione;
	}
	
	
	
	
	
	
	
	
	
	
}
