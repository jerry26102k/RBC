package com.rajheshbuilders.rbc;

public class ModelData {

    private String heading,description,location,area;
    int imageUri;




    public ModelData(String heading, String description, String location, String area, int imageUri) {
        this.heading = heading;
        this.description = description;
        this.location = location;
        this.area = area;
        this.imageUri = imageUri;

    }

    public String getmHeader() {
        return heading;
    }

    public void setmHeader(String mHeader) {
        this.heading = heading;
    }

    public String getmDesc() {
        return description;
    }

    public void setmDesc(String mDesc) {
        this.description = description;
    }

    public String getmLocation() {
        return location;
    }

    public void setmLocation(String mLocation) {
        this.location = location;
    }

    public String getmArea() {
        return area;
    }

    public void setmArea(String mArea) {
        this.area = area;
    }

    public int  getmImg() {
        return imageUri;
    }

    public void setmImg(int mImg) {
        this.imageUri = imageUri;
    }


}
