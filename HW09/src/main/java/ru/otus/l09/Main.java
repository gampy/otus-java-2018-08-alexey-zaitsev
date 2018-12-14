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

        System.out.println("----------------------------------------------------------");
        System.out.println("\n\rInteger object toJson:");

        int intg = 123;

        System.out.println("\n\rGson:");

        Gson gson2 = new Gson();
        String json2 = gson2.toJson(intg);
        System.out.println(json2);

        System.out.println("\n\rMyGson:");

        MyGson myGson2 = new MyGson();
        String myJson2 = myGson2.toJson(intg);
        System.out.println(myJson2);

        System.out.println("----------------------------------------------------------");
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

        System.out.println("----------------------------------------------------------");
        System.out.println("\n\rBoolean object toJson:");

        boolean bln = false;

        System.out.println("\n\rGson:");

        Gson gson4 = new Gson();
        String json4 = gson4.toJson(bln);
        System.out.println(json4);

        System.out.println("\n\rMyGson:");

        MyGson myGson4 = new MyGson();
        String myJson4 = myGson4.toJson(bln);
        System.out.println(myJson4);





    }
}

