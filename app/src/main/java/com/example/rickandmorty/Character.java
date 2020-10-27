package com.example.rickandmorty;

import java.io.Serializable;

public class Character implements Serializable {
    private int ID;
    private String name;
    private String status;
    private String lastKnownLoc;
    private int image;

    public Character(int id, String name, String status, String lastKnownLoc, int image) {
        ID = id;
        this.name = name;
        this.status = status;
        this.lastKnownLoc = lastKnownLoc;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastKnownLoc() {
        return lastKnownLoc;
    }

    public void setLastKnownLoc(String lastKnownLoc) {
        this.lastKnownLoc = lastKnownLoc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Character{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", lastKnownLoc='" + lastKnownLoc + '\'' +
                ", image=" + image +
                '}';
    }
}
