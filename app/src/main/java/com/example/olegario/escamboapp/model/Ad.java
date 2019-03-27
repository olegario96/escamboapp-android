package com.example.olegario.escamboapp.model;

public class Ad {
    private String title;
    private String description;
    private double price;

    public Ad() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;

    }
}
