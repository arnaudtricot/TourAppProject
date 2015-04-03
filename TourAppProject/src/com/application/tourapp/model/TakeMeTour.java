package com.application.tourapp.model;

/**
 * @author Ludovic
 *
 * Permet de créer les éléments de parcours à effectuer
 */
public class TakeMeTour {

	private int id;
	private Item morningItem;
	private Item afternonItem;
	private Item nightItem;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Item getMorningItem() {
		return morningItem;
	}
	public void setMorningItem(Item morningItem) {
		this.morningItem = morningItem;
	}
	public Item getAfternoonItem() {
		return afternonItem;
	}
	public void setAfternonItem(Item afternonItem) {
		this.afternonItem = afternonItem;
	}
	public Item getNightItem() {
		return nightItem;
	}
	public void setNightItem(Item nightItem) {
		this.nightItem = nightItem;
	}
	
	

}
