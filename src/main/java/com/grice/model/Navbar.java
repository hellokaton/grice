package com.grice.model;

import java.util.ArrayList;
import java.util.List;

public class Navbar {
	
	private List<Item> items = new ArrayList<Item>();

	public Navbar() {

	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
}
