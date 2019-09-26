package com.example.prectify;

public class UserData {

    private String qDescription;
    private String image;



    public UserData(){

    }
    public UserData (  String qDescription , String image ) {

        this.qDescription = qDescription;
        this.image = image;
    }











    public String getqDescription () {
        return qDescription;
    }

    public String getImage () {

        return image;
    }
}
