package com.rajheshbuilders.rbc;

public class DataModelForServices {
    private String mSerHeader;
    private int mSerImg,mColorId;

    public String getmSerHeader() {
        return mSerHeader;
    }

    public void setmSerHeader(String mSerHeader) {
        this.mSerHeader = mSerHeader;
    }

    public int getmSerImg() {
        return mSerImg;
    }

    public void setmSerImg(int mSerImg) {
        this.mSerImg = mSerImg;
    }

    public int getmColorId() {
        return mColorId;
    }

    public void setmColorId(int mColorId) {
        this.mColorId = mColorId;
    }

    public DataModelForServices(String mSerHeader, int mColorId, int mSerImg) {
        this.mSerHeader = mSerHeader;
        this.mSerImg = mSerImg;
        this.mColorId = mColorId;
    }
}
