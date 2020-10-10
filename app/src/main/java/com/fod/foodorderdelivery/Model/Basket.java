package com.fod.foodorderdelivery.Model;

public class Basket {
    private String FoodId;
    private String foodName;
    private int quantity;
    private double price;

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Basket(String foodId, String foodName, int quantity, double price) {
        FoodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
    }


}
