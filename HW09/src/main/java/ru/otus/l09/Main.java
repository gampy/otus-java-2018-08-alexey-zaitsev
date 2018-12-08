package ru.otus.l09;

import com.google.gson.Gson;
import ru.otus.l09.example.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("\n\rDemo object toJson:");

        Demo demo = new Demo();

        System.out.println("\n\rGson:");

        Gson gson1 = new Gson();
        String json1 = gson1.toJson(demo);
        System.out.println(json1);

        System.out.println("\n\rMyGson:");

        MyGson myGson1 = new MyGson();
        String myJson1 = myGson1.toJson(demo);
        System.out.println(myJson1);

        //----------------------------------------------------------
        System.out.println("\n\rString object toJson:");

        String str = "Hello, world!";


        System.out.println("\n\rGson:");

        Gson gson3 = new Gson();
        String json3 = gson3.toJson(str);
        System.out.println(json3);

        System.out.println("\n\rMyGson:");

        MyGson myGson3 = new MyGson();
        String myJson3 = myGson3.toJson(str);
        System.out.println(myJson3);
    }
}

