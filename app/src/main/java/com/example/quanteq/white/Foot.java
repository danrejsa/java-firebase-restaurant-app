package com.example.quanteq.white;

/**
 * Created by Quanteq on 2/19/2018.
 */

public class Foot {

    private String name, price,image,desc;
    public Foot(){

    }
    public Foot (String name, String price, String image, String desc){
        this.name=name;
        this.desc=desc;
        this.price=price;
        this.image=image;

    }

    public String getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

