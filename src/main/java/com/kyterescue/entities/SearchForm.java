package com.kyterescue.entities;

public class SearchForm {
    private int zipcode;
    private String age;
    private String size;
    private String type;
    private int page;

    public SearchForm() {

    }

    public SearchForm(String type, String age, String size, int zipcode, int page) {
        this.zipcode = zipcode;
        this.age = age;
        this.size = size;
        this.type = type;
        this.page = page;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
