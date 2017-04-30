package com.weatherAPI.userProfileManager;

import java.io.Serializable;

public class UserProfileBean implements Serializable {
    private String name;
    private String username;
    private byte[] password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}
