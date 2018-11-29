package com.example.michellemedina.bakingapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Step implements Serializable {

    @SerializedName("id")
    private Integer stepId;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public Integer getStepId() {
        return stepId;
    }

    public void setId(Integer id) {
        this.stepId = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

}
