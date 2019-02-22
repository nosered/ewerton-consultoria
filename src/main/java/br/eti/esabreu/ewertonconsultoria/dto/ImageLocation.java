package br.eti.esabreu.ewertonconsultoria.dto;

import java.io.Serializable;

public class ImageLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
