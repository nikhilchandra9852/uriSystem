package com.service.Tiny.Url.Service.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class UriEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id ;

    private String longUrl;

    private String shortUrl;

    private LocalDateTime dateTime;

    public UriEntity() {
    }

    public UriEntity(String longUrl, String shortUrl, LocalDateTime dateTime) {
        this.id = UUID.randomUUID().toString();
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.dateTime = dateTime;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
