package com.example.prectify;

public class UserData {

    private String qDescription;
    private String image;
    private String qTitle;




    public UserData(){

    }
    public UserData (  String qDescription , String image , String qTitle) {

        this.qDescription = qDescription;
        this.image = image;
        this.qTitle=qTitle;
    }

    public String getqTitle(){
        return qTitle;
    }

    public String getqDescription () {
        return qDescription;
    }

    public String getImage () {
        return image;
    }
}


