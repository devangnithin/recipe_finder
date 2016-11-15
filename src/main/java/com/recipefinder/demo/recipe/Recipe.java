package com.recipefinder.demo.recipe;

import com.recipefinder.demo.Ingredient;

public class Recipe {
	String name;
	Ingredient[] ingredients;
	public Ingredient[] getIngredients() {
		return ingredients;
	}
	public void setIngredients(Ingredient[] ingredients) {
		this.ingredients = ingredients;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
