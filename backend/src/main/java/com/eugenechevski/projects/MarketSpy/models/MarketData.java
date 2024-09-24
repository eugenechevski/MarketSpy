package com.eugenechevski.projects.MarketSpy.models;

import jakarta.persistence.*;

@Entity
@Table(name = "market_data")
public class MarketData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float price;

    private String timestamp;

    public MarketData(String name, Float price, String timestamp) {
        this.name = name;
        this.price = price;
        this.timestamp = timestamp;
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}