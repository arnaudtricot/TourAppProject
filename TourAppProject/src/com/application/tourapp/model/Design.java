package com.application.tourapp.model;

/**
 * @author Ludovic
 * 
 * Class model représentant le design de l'application
 */
public class Design {

	private int id;
	private String name;
	private String colorTitleItem;
	private String colorDescriptionItem;
	private String colorBackground;
	private byte[] logoApplication;

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

	public String getColorTitleItem() {
		return colorTitleItem;
	}

	public void setColorTitleItem(String colorTitleItem) {
		this.colorTitleItem = colorTitleItem;
	}

	public String getColorDescriptionItem() {
		return colorDescriptionItem;
	}

	public void setColorDescriptionItem(String colorDescriptionItem) {
		this.colorDescriptionItem = colorDescriptionItem;
	}

	public byte[] getLogoApplication() {
		return logoApplication;
	}

	public void setLogoApplication(byte[] logoApplication) {
		this.logoApplication = logoApplication;
	}
	
	public String getColorBackground()  {
		return colorBackground;
	}

	public void setColorBackground(String colorBackground) {
		this.colorBackground = colorBackground;
	}
}
