package com.example.MyRefrigerator;

public class Griditem {

    private String name;
    private String data;
    private String str;

    Griditem(String name, String data, String str){
        this.name = name;
        this.data = data;
        this.str = str;

    }

    Griditem(){

    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getStr() {
        return str;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
