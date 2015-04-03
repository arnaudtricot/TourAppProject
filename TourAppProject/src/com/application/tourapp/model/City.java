package com.application.tourapp.model;

/**
 * @author Ludovic
 * 
 * Class model représentant les villes disponibles sur l'application
 */
public class City {
	
	/*
	 * EGA - la modification des attribue de la classe des types
	 */
	private int id;
	private String name ;
	private boolean selected;
	
	public City(String name){
		id = 9999;
		this.name = name;
		selected = false;
	}
	public City(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return getName();
	}
}
