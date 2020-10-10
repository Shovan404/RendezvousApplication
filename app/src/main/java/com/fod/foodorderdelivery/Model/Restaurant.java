package com.fod.foodorderdelivery.Model;

public class Restaurant {

    private String _id;
    private String name;
    private String about;
    private String location;
    private String image;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Restaurant( String name, String about, String location, String image) {
        this.name = name;
        this.about = about;
        this.location = location;
        this.image = image;
    }


}
