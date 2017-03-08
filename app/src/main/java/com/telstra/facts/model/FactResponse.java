package com.telstra.facts.model;

/**
 * Created by ravi.gami on 3/8/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class FactResponse implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("rows")
    @Expose
    private ArrayList<Fact> facts;

    public ArrayList<Fact> getFacts() {
        return facts;
    }

    public void setFacts(ArrayList<Fact> facts) {
        this.facts = facts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Results can be filtered here based on filter criteria.
     * @return
     */
    public ArrayList<Fact> getFilteredFacts() {
        ArrayList<Fact> filteredFacts = new ArrayList<>();
        for (Fact fact:this.facts) {
            if (! (StringUtils.isEmpty(fact.getTitle()) && StringUtils.isEmpty(fact.getDescription()) && StringUtils.isEmpty(fact.getImageUrl()))){
                filteredFacts.add(fact);
            }
        }
        return filteredFacts;
    }
}