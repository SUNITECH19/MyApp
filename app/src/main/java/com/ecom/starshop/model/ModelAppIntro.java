package com.ecom.starshop.model;

public class ModelAppIntro extends ModelImage {

    private String body;

    public ModelAppIntro(String title, String body, int imageRes, boolean isSelected ) {
        super(null, title, imageRes, isSelected);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
