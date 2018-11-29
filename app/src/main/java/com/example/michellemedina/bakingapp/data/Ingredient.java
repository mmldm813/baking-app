package com.example.michellemedina.bakingapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {

        @SerializedName("quantity")
        private Double quantity;

        @SerializedName("measure")
        private String measure;

        @SerializedName("ingredient")
        private String ingredient;

        public Double getQuantity() {
            return quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }

}
