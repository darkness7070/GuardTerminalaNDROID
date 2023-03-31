package com.example.guardterminal;

public class Applications {
    private String name;
    private String date;
    private String subdivision;
    private String type;

    public Applications(String name, String date, String subdivision, String type) {
        this.name = name;
        this.date = date;
        this.subdivision = subdivision;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
