package com.recipefinder.demo.fridge;

public class Fridge {
	FridgeContents[] fridgeContents;
	
	public Fridge() {
		
	}
	
public Fridge(FridgeContents[] fridgeContents) {
	this.fridgeContents = fridgeContents;
	}

public FridgeContents[] getFridgeContents() {
	return fridgeContents;
}

public void setFridgeContents(FridgeContents[] fridgeContents) {
	this.fridgeContents = fridgeContents;
}
}
