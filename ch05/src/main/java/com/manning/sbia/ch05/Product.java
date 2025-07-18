package com.manning.sbia.ch05;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {
	@Id
	private String id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private float price;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public float getPrice() { return price; }
	public void setPrice(float price) { this. price = price; }
}

