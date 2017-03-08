package com.telstra.facts.model;

/**
 * Created by ravi.gami on 3/8/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Error implements Serializable {
    @SerializedName("error")
    @Expose
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}