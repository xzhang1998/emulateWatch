package com.example.emulatewatch.helper;

public class Applications {
    private String appName;
    private String imageUrl;

    public Applications(String appName, String imageUrl) {
        this.appName = appName;
        this.imageUrl = imageUrl;
    }

    public String getAppName() {
        return appName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
