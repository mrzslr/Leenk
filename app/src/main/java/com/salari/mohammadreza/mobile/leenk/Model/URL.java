package com.salari.mohammadreza.mobile.leenk.Model;



/**
 * Created by MohammadReza on 11/01/2016.
 */
public class URL {

    private long id;
    private String title;
    private String longUrl;
    private String shortUrl;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }


}