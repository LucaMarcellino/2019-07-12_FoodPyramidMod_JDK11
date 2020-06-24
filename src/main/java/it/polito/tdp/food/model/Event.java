package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		INIZIO_PREPARAZIONE,FINE_PREPARAZIONE
	}
	
	private Double time;
	private EventType type;
	private Stazione stazione;
	private Food food;
	public Event(double time, Stazione stazione, Food food, EventType type) {
		super();
		this.type=type;
		this.time = time;
		this.stazione = stazione;
		this.food = food;
	}
	public Double getTime() {
		return time;
	}
	public Stazione getStazione() {
		return stazione;
	}
	public Food getFood() {
		return food;
	}
	
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	@Override
	public int compareTo(Event o) {
		
		return this.time.compareTo(o.getTime());
	}

	
	
}
