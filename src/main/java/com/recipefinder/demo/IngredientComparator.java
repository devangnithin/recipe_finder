package com.recipefinder.demo;

import java.util.Comparator;

import com.recipefinder.demo.Ingredient;

class IngredientComparator implements Comparator<Ingredient> {
    public int compare(Ingredient o1, Ingredient o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}