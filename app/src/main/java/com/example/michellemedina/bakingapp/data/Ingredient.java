package com.example.michellemedina.bakingapp.data;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

        @SerializedName("quantity")
        private Double quantity;

        @SerializedName("measure")
        private String measure;

        @SerializedName("ingredient")
        private String ingredient;

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

}
