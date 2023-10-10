package com.kyterescue.entities;

public class SearchForm {
    private String category;
    private int zipcode;

    public SearchForm() {

    }
    public SearchForm(String category) {
        this.category = category;
    }
    public SearchForm(int zipcode) {
        this.zipcode = zipcode;
    }
    public SearchForm(String category, int zipcode) {
        this.category = category;
        this.zipcode = zipcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
