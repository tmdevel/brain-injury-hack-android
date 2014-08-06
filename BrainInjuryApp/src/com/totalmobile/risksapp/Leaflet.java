package com.totalmobile.risksapp;

public class Leaflet {
	private String title;
	private int color;
	private int pdf;

	public Leaflet(String title, int color, int pdf) {
		super();
		this.title = title;
		this.color = color;
		this.pdf = pdf;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getPdf() {
		return pdf;
	}

	public void setPdf(int pdf) {
		this.pdf = pdf;
	}

}
