package com.tallercm.app6;

public class Product {
    private int numProd;
    private String id;
    private String title;
    private String description;
    private String imageUrl;


    public Product(int numProd, String id, String title, String description, String imageUrl) {
        this.numProd = numProd;
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void setNumProd(int numProd) {
        this.numProd = numProd;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumProd() {
        return numProd;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
