package com.example.prectify;

public class UserData {
    private String qTitle;
    private String qDescription;
    private int image;

    public UserData ( String qTitle , String qDescription , int image ) {
        this.qTitle = qTitle;
        this.qDescription = qDescription;
        this.image = image;
    }








    public String getqTitle () {
        return qTitle;
    }

    public String getqDescription () {
        return qDescription;
    }

    public int getImage () {

        return image;
    }
}
