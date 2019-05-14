package com.example.azeee.mob;
public class GetSet {
  public   String email;
   public String uid ;
   public String firebaseToken;
   public String name;
String count;
 //  public  String imageurl;


    public GetSet() {

    }
    public GetSet(String uid,String name) {
  this.uid=uid;
  this.name = name;
    }


    public  GetSet(String uid,String email,String name){
        this.uid = uid;
        this.email=email;
       //this.firebaseToken=firbasetoken;
      this.name = name;

    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String uname) {
        this.name = uname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
