package com.example.bageesdemo;

public class Product {
    private String pname,price,image,pid,description;

    public Product(){}

    public Product(String pname, String price, String image, String pid, String description) {
        this.pname = pname;
        this.price = price;
        this.image = image;
        this.pid = pid;
        this.description = description;

    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
