package com.example.cashbookproject.models;

public class Account {

    private double amount;
    private String name;

    private int image;

    public Account() {
    }

    public Account(double amount, String name, int image) {
        this.amount = amount;
        this.name = name;
        this.image = image;
    }

    public Account(double amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
