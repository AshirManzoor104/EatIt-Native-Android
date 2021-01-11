package com.example.eatit;

public class slideItem {
    private String slidesId;
    private String slideImage;
    private String slideCaption;
    private String slideStatus;

    public slideItem() {
    }

    public slideItem(String slidesId, String slideImage, String slideCaption, String slideStatus) {
        this.slidesId = slidesId;
        this.slideImage = slideImage;
        this.slideCaption = slideCaption;
        this.slideStatus = slideStatus;
    }

    public String getSlidesId() {
        return slidesId;
    }

    public void setSlidesId(String slidesId) {
        this.slidesId = slidesId;
    }

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }

    public String getSlideCaption() {
        return slideCaption;
    }

    public void setSlideCaption(String slideCaption) {
        this.slideCaption = slideCaption;
    }

    public String getSlideStatus() {
        return slideStatus;
    }

    public void setSlideStatus(String slideStatus) {
        this.slideStatus = slideStatus;
    }
}
