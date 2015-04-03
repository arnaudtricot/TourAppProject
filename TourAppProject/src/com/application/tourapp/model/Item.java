package com.application.tourapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ludovic
 *
 * Class model représentant les items touristiques
 */
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	private String prix;
	private String email;
	private String address;
	private String website;
	private String longitude;
	private String latitude;
	private String number;
	private int imageNumber;
	private Type type;
	private City city;
	private byte[] imageOne;
	private byte[] imageTwo;
	private byte[] imageThree;
	private List<byte[]> listImages;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<byte[]> getListImages() {
		return listImages;
	}

	public void setListImages(List<byte[]> listImages) {
		this.listImages = listImages;
	}

	public byte[] getImageOne() {
		return imageOne;
	}

	public void setImageOne(byte[] imageOne) {
		this.imageOne = imageOne;
	}

	public byte[] getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(byte[] imageTwo) {
		this.imageTwo = imageTwo;
	}

	public byte[] getImageThree() {
		return imageThree;
	}

	public void setImageThree(byte[] imageThree) {
		this.imageThree = imageThree;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
