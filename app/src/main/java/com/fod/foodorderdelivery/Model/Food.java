package com.fod.foodorderdelivery.Model;

public class Food {

    private String _id;
    private String foodName;
    private String foodPrice;
    private String description;
    private String image;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Food(String foodName, String restaurants, String foodPrice, String description, String image) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.description = description;
        this.image = image;
    }


}
