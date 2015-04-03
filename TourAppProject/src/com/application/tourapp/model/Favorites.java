package com.application.tourapp.model;

import java.util.List;

/**
 * @author Ludovic
 *
 * Class model représentant les items touristiques favoris
 */
public class Favorites {

	private List<Item> listFavorits;

	public List<Item> getListFavorits() {
		return listFavorits;
	}

	public void setListFavorits(List<Item> listFavorits) {
		this.listFavorits = listFavorits;
	}
	
}
