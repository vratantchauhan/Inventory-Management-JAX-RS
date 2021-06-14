package com.vratant.jerseyapi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Inventory {

	private int productId;
	private String productName;
	private double cost;
	private boolean availablity;
	
	public boolean isAvailablity() {
		return availablity;
	}
	public void setAvailablity(boolean availablity) {
		this.availablity = availablity;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "Inventory [productId=" + productId + ", productName=" + productName + ", cost=" + cost
				+ ", availablity=" + availablity + "]";
	}
	
	
}
