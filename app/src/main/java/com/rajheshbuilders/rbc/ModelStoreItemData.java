package com.rajheshbuilders.rbc;

public class ModelStoreItemData {

    String Name,Price,ImageUri;
    ModelStoreItemData(){

    }

    public ModelStoreItemData(String Name, String Price, String ImageUri) {
        this.Name = Name;
        this.Price = Price;
        this.ImageUri = ImageUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String ImageUri) {
        this.ImageUri = ImageUri;
    }
}
