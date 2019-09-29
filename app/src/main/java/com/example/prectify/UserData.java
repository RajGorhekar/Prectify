package com.example.prectify;

public class UserData {

    private String qDescription;
    private String image;
    private String  qtitle;




    public UserData(){

    }
    public UserData (  String qDescription , String image , String qtitle) {

        this.qDescription = qDescription;
        this.image = image;
        this.qtitle=qtitle;
    }









    public String   getqTitle(){
        return qtitle;
    }

    public String getqDescription () {
        return qDescription;
    }

    public String getImage () {

        return image;
    }
}
