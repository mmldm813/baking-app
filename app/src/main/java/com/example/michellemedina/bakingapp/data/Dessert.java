package com.example.michellemedina.bakingapp.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Dessert {

    @SerializedName("id")
    private Integer dessertId;

    @SerializedName("name")
    private String dessertName;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients = null;

    @SerializedName("steps")
    private List<Step> steps = null;

    @SerializedName("servings")
    private Integer servings;

    @SerializedName("image")
    private String image;

    public Integer getDessertId() {
        return dessertId;
    }

    public void setDessertId(Integer id) {
        this.dessertId = id;
    }

    public String getDessertName() {
        return dessertName;
    }

    public void setName(String name) {
        this.dessertName = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
