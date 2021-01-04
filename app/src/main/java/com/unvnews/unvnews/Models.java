package com.unvnews.unvnews;

public class Models {
    private String API_KEY;

    public String getBASE_URL() {
        return "https://newsapi.org/v2/";
    }

    public String getCOUNTRY() {
        return "in";
    }

    public String getAPI_KEY() {
        return "bc20278d342b4f8b8398f08df743c1ed";
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }
}
