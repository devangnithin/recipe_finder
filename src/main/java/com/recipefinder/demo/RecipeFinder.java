package com.recipefinder.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipefinder.demo.exception.RecipeNotFoundException;
import com.recipefinder.demo.fridge.Fridge;
import com.recipefinder.demo.fridge.FridgeContents;
import com.recipefinder.demo.Ingredient;
import com.recipefinder.demo.recipe.Recipe;

public class RecipeFinder {
	
	Recipe[] recipeArray;
	FridgeContents[] fridgeContents;
	
	
	
	public List<Recipe> recommendCooking(Recipe[] recipeArray) {
		List<Recipe> recommendedRecipeList = new ArrayList<Recipe>();;
		
		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
		
		//int ingredientCount =  0;
		for (int i = 0; i < recipeArray.length ;  i++) {
			Ingredient[] recipeIngredients = recipeArray[i].getIngredients();
			for (int j = 0;  j < recipeIngredients.length ; j++) {
				ingredientsList.add(recipeIngredients[j]); 
			}
		}		
		Collections.sort(ingredientsList, new IngredientComparator());
		Date criticalExpiry = null;
		if (ingredientsList.size() > 0 ) {
			criticalExpiry = ingredientsList.get(0).getDate();
		}
		
		for (Ingredient ingredient: ingredientsList) {
			if(ingredient.getDate().compareTo(criticalExpiry) == 0){
				for (Recipe recipe : recipeArray) {
					Ingredient[] recipeIngredientArray = recipe.getIngredients();
					for (Ingredient recipeIngredient: recipeIngredientArray) {
						if (recipeIngredient.getItem().equalsIgnoreCase(ingredient.getItem())){
							recommendedRecipeList.add(recipe);
							
						}
					}
				}
			}
		}		
		
		return recommendedRecipeList;
		
	
	}
	
	public Recipe[] whatCanCooked(String recipeFile, String fridgeFile) throws Exception {
		
		fridgeContents = populateFridgeContents(fridgeFile);
		Fridge fridge = new Fridge();
		fridge.setFridgeContents(populateFridgeContents(fridgeFile));
		recipeArray = populateRecipe(recipeFile, fridge);
		
		Recipe[] recipeCanCookArray ;

		int countOfProducableRecipe = 0;
		Recipe[] recipeCanCookArrayTemp = new Recipe[recipeArray.length];
		for (Recipe recipe : recipeArray) {
			if(canCookCheck(recipe, fridge.getFridgeContents())) {
				//System.out.println("Yes");
				recipeCanCookArrayTemp[countOfProducableRecipe] = recipe;
				countOfProducableRecipe++;
			}
		}
		
		recipeCanCookArray = new Recipe[countOfProducableRecipe];
		
		System.arraycopy(recipeCanCookArrayTemp, 0, recipeCanCookArray, 0, countOfProducableRecipe);

		return recipeCanCookArray;
	}
		
	private Boolean canCookCheck(Recipe recipe, FridgeContents[] fridgeContentsArray) {
		Boolean flag = false;
		//System.out.println(recipe.getName());
		try {
			Ingredient[] ingredients = recipe.getIngredients();
			// String[] canCookRecipe = new String[100];
			Date date = new Date();
			int count = 0;
			for (int i = 0; i < ingredients.length; i++) {
				for (int j = 0; j < fridgeContentsArray.length; j++) {
					if (ingredients[i].getItem().equalsIgnoreCase(fridgeContentsArray[j].getItem())) {
						if (fridgeContentsArray[j].getUseBy().compareTo(date) >= 0) {
							if(fridgeContentsArray[j].getAmount() >= ingredients[i].getAmount()) {
								count++;
							}
						}
						break;
					}
				}
			}

			if (count == ingredients.length) {
				flag = true;
			} else {
				throw new RecipeNotFoundException();
			}

		} catch (RecipeNotFoundException e) {
			flag = false;
		}

		return flag;
	}

	private FridgeContents[] populateFridgeContents(String fridgeFile) throws Exception {
		FridgeContents[] fridgeContentsTemp = new FridgeContents[100];
		BufferedReader br = new BufferedReader(new FileReader(fridgeFile));
		String line = "";
		int count = 0;
		while ((line = br.readLine()) != null) {
			String[] fields = line.split(",");
			fridgeContentsTemp[count] = new FridgeContents(fields[0], fields[1], fields[2], fields[3]);
			count++;
		}

		FridgeContents[] fridgeContents = new FridgeContents[count];
		System.arraycopy(fridgeContentsTemp, 0, fridgeContents, 0, count);
		return fridgeContents;

	}

	private Recipe[] populateRecipe(String recipeFile, Fridge fridge) throws Exception {
		Recipe[] recipe;
		FridgeContents[] fridgeContents = fridge.getFridgeContents();
		JSONParser parser = new JSONParser();
		JSONArray recipeArray = (JSONArray) parser
				.parse(new FileReader(recipeFile));
		recipe = new Recipe[recipeArray.size()];

		for (int i = 0; i < recipeArray.size(); i++) {
			ObjectMapper mapper = new ObjectMapper();
			recipe[i] = mapper.readValue(recipeArray.get(i).toString(), Recipe.class);
			Ingredient[] ingredientArray = recipe[i].getIngredients();
			for(Ingredient ingredient: ingredientArray) {
				for(int j = 0; j < fridgeContents.length; j++) {
					if(ingredient.getItem().equalsIgnoreCase(fridgeContents[j].getItem())) {
						ingredient.setDate(fridgeContents[j].getUseBy());
					}
				}
			}
			//System.out.println(recipeArray.get(i).toString());
		}
		return recipe;
	}


}
