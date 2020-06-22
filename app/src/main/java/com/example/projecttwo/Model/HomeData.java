package com.example.projecttwo.Model;

public class HomeData {
    private int imgId;
    private String title;
    private String description;
    private Class aClass;



    public HomeData(int imgId, String title, String description, Class dataClass){
        this.imgId = imgId;
        this.title =title;
        this.description = description;
        this.aClass = dataClass;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
