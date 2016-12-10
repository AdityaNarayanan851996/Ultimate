package com.example.myfinal.ultimate.models;

/**
 * Created by Nithya on 06-Dec-16.
 */

public class PhotoModel {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;


    public String getUrl_n() {
        return url_n;
    }

    private String url_n;
    private String height_n;
    private String width_n;


    public void setUrl_n(int f,String ser,String i,String se) {
        this.url_n = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_n.jpg";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeight_n() {
        return height_n;
    }

    public void setHeight_n(String height_n) {
        this.height_n = height_n;
    }

    public String getWidth_n() {
        return width_n;
    }

    public void setWidth_n(String width_n) {
        this.width_n = width_n;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }






















/*
            "id": "30705344734",
            "owner": "129650098@N03",
            "secret": "6d64ac8f88",
            "server": "707",
            "farm": 1,
            "title": "Happy Anniversary to Jose on your #Kia #Sportage from Marcus Chavez at Van Griffith Kia!",
            "ispublic": 1,
            "isfriend": 0,
            "isfamily": 0,
            "url_n": "https:\/\/farm1.staticflickr.com\/707\/30705344734_6d64ac8f88_n.jpg",
            "height_n": 220,
            "width_n": "320"
*/





}
