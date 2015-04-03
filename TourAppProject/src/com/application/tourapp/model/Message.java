package com.application.tourapp.model;

/**
 * @author Ludovic
 *
 * Class model représentant les messages à afficher
 */
public class Message {

	private int id;
	private String subject;
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
