package test;

import database.MyConnect;

public class MyConnectTest {
    public static void main(String[] args) {
        MyConnect myConnect = new MyConnect();
        myConnect.findActorByID(10);


        System.out.println("Get actor count: "+myConnect.getActorCount());
        System.out.println("actor 90 delete: "+ myConnect.deleteActor(20));
//        System.out.println("Get actor count: "+myConnect.getActorCount());
    }
}
