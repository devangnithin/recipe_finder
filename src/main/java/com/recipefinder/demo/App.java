package com.recipefinder.demo;

import java.util.List;

import com.recipefinder.demo.recipe.Recipe;

/**
 * Hello world!
 *
 */

public class App {
	public static void main(String[] args) throws Exception {


		RecipeFinder recipeFinder =  new RecipeFinder();
		
		String fridgeFilePath = "D:\\demo\\camel\\camel-demo\\in\\fridge.csv";
		String  recipeFilePath = "D:\\demo\\camel\\camel-demo\\in\\recipe.json";
		
		Recipe[] recipeArray = recipeFinder.whatCanCooked(recipeFilePath, fridgeFilePath);
		
		System.out.println("Recipe Finder has identified based on the ingredients available in your fridge you can cook");
		for (int i = 0; i < recipeArray.length; i++) {
			System.out.println(recipeArray[i].getName());
		}
		
		List<Recipe> recommended = recipeFinder.recommendCooking(recipeArray);
		
		System.out.println("We recommend you cook");
		for (Recipe recomRecipe : recommended) {
			System.out.println(recomRecipe.getName());
		}
	}
}
