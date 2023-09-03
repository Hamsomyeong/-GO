package com.example.MyRefrigerator;

public class Listitem {

    //재료명, 구입 or 보관 날짜, 보관기간으로부터 지난일
    private String name;
    private String data;
    private String day;
    private String icon;

    //생성자
    Listitem(String name, String data, String day, String icon){
        this.name = name;
        this.data = data;
        this.day= day;
        this.icon = icon;
    }

    Listitem(){

    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getDay() {
        return day;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
