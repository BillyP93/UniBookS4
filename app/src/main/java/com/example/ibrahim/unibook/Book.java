package com.example.ibrahim.unibook;

/**
 * Created by ibrahim on 2/19/2017.
 */

public class Book {
    private String title,desc,price,image;



    public Book(String title, String desc, String price, String image) {
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.image = image;
    }

    public Book(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}
