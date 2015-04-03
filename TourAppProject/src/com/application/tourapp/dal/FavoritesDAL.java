package com.application.tourapp.dal;

import android.content.Context;

import com.application.tourapp.databasehandler.FavoritesDatabaseHandler;
import com.application.tourapp.model.Favorites;
import com.application.tourapp.model.Item;

public class FavoritesDAL {
	
	FavoritesDatabaseHandler favoritesDB;
	
	public FavoritesDAL(Context context) {
		favoritesDB = new FavoritesDatabaseHandler(context);
	}
	
	
	public Favorites getAllFavorites() {
		return favoritesDB.getAllFavorites();
	}
	
	public void addFavorit(Item item) {
		favoritesDB.addFavorit(item);
	}
	
	public boolean existsFavorit(Item item) {
		return favoritesDB.existsFavorit(item);
	}
	
}
