package com.example.prectify;

public class UserData {

    private String qDescription;
    private String image;
    private String qTitle;
    private String token;
    private String status;
   // private int post;




    public UserData(){

    }



    public UserData (String qDescription , String image , String qTitle, String token, String status) {

        this.qDescription = qDescription;
        this.image = image;
        this.qTitle=qTitle;
        this.token = token;
        this.status= status;
        //this.post=post;
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
    public String getStatus() {
        return status;
    }
   // public int getPost(){     return post; }


}


