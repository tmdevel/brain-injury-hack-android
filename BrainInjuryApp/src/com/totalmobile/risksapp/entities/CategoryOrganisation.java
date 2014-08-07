package com.totalmobile.risksapp.entities;


public class CategoryOrganisation extends DbEntity{

	private int id;
	private int categoryId;
	private int organisationId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public int getOrganisationId() {
		return organisationId;
	}
	
	public void setOrganisationId(int organisationId) {
		this.organisationId = organisationId;
	}
	
}
