package com.fod.foodorderdelivery.ServerResponse;

public class UserResponse {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String status;
    private String token;

    public UserResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }
}