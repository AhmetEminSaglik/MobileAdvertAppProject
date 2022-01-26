package com.example.ilanvermemobilprojeodevi.db.advert;

import android.view.View;

public class Advert {
    private String id;
    private String title, description, imageString, date;
    private String price;
    private String userId;
    private View.OnClickListener clickListener;

    public Advert() {
    }

    public Advert(String id, String title, String description, String imageString, String price, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageString = imageString;
        this.date = date;
        this.price = price;
        this.userId = userId;
    }

 /*   public Advert(String id, String title, String description, String image, String date, String price, String userId, View.OnClickListener clickListener) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.price = price;
        this.userId = userId;
        this.clickListener = clickListener;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageString() {
        if (imageString == null) {
            imageString = " ";
        }
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public String getDate() {
//        return date;
//    }
//    public void setDate(String date) {
//        this.date = date;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageString='" + imageString + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
