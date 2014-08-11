package com.totalmobile.risksapp;

import android.net.Uri;

public class Leaflet {
	private String title;
	private int color;
	private Uri pdf;

	public Leaflet(String title, int color, Uri pdf) {
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

	public Uri getPdf() {
		return pdf;
	}

	public void setPdf(Uri pdf) {
		this.pdf = pdf;
	}

}
