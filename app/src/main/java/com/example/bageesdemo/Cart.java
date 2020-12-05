package com.example.bageesdemo;

public class Cart {
 private String pid, price, productname,image;

    public Cart() {
        
    }

    public Cart(String pid, String price, String productname, String image) {
        this.pid = pid;
        this.price = price;
        this.productname = productname;
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
