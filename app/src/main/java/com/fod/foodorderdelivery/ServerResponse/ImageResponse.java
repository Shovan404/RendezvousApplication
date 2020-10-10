package com.fod.foodorderdelivery.ServerResponse;

public class ImageResponse {
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ImageResponse(String filename) {
        this.filename = filename;
    }

    private String filename;
}
