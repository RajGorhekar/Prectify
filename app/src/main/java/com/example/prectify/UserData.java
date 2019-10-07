package com.example.prectify;

public class UserData {

    private String qDescription;
    private String image;
    private String qTitle;
    private String token;




    public UserData(){

    }
    public UserData (  String qDescription , String image , String qTitle,String token) {

        this.qDescription = qDescription;
        this.image = image;
        this.qTitle=qTitle;
        this.token = token;
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

    public String getToken() {
        return token;
    }
}


