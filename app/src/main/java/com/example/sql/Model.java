package com.example.sql;

public class Model {
    String title;
    boolean checkstate;

    public Model(String title, boolean checkstate) {
        this.title = title;
        this.checkstate=checkstate;

    }

    public String getTitle() {
        return title;
    }

    public boolean isCheckstate() {
        return checkstate;
    }
}
