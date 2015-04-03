package com.application.tourapp.model;

import java.io.Serializable;

/**
 * @author Ludovic
 *
 * Class model permettant de créer des types d'activités
 */
public class Type implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * EGA - la modification des attribue de la classe des types
	 */
	private int id;
	private String name ;	
	private byte[] imageBlob;
	//imageNumber;
	
	
	
	
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
	/*public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}*/
	
	public byte[] getImageBlob() {
		return imageBlob;
	}
	public void setImageBlob(byte[] imageBlob) {
		this.imageBlob = imageBlob;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
