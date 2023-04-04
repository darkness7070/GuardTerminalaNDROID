package com.example.guardterminal;

import java.io.Serializable;

public class Applications implements Serializable {
    private int id;
    private String name;
    private String date;
    private String subdivision;
    private String type;

    public Applications(int id,String name, String date, String subdivision, String type) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.subdivision = subdivision;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
