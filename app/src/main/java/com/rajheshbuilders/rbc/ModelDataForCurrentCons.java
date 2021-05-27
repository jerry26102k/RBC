package com.rajheshbuilders.rbc;

public class ModelDataForCurrentCons {
    private String Heading,Description,Location,Area,Status;
    private String ImageUri;
    ModelDataForCurrentCons(){

    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String Heading) {
        this.Heading = Heading;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String ImageUri) {
        this.ImageUri = ImageUri;
    }

    public ModelDataForCurrentCons(String Heading, String Description, String Location, String Area, String Status, String ImageUri) {
        this.Heading = Heading;
        this.Description = Description;
        this.Location = Location;
        this.Area = Area;
        this.Status = Status;
        this.ImageUri = ImageUri;
    }
}
