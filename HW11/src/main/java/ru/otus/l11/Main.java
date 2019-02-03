package ru.otus.l11;

import ru.otus.l11.ORM.DBService;
import ru.otus.l11.ORM.hibernate.HibernateDBService;
import ru.otus.l11.ORM.myORM.MyOrmDBService;
import ru.otus.l11.dataSets.AddressDataSet;
import ru.otus.l11.dataSets.PhoneDataSet;
import ru.otus.l11.dataSets.UserDataSet;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        DBService dbService1 = new MyOrmDBService();
        DBService dbService2 = new HibernateDBService();


        UserDataSet user1 = new UserDataSet(
                    "Вася",
                    19,
                    List.of( new AddressDataSet("Ромашково")),
                    new PhoneDataSet("+7 123 456 7890"),
                    new PhoneDataSet("+7 495 654 3210")
                );
        UserDataSet user2 = new UserDataSet(
                    "Марьяванна",
                    62,
                    List.of( new AddressDataSet("Васильково")),
                    new PhoneDataSet("+7 499 456 7890"),
                    new PhoneDataSet("+7 499 654 3210")
                );


        dbService1.saveUser(user1);
        dbService1.saveUser(user2);

        dbService2.saveUser(user1);
        dbService2.saveUser(user2);

        long id = 2;

        Object o1 = dbService1.readUser(id);
        if (o1 != null) {
            System.out.println(o1.toString());
        } else {
            System.out.println("Нет записей с id = " + id);
        }

        id = 2;
        UserDataSet o2 = dbService2.readUser(id);
        if (o2 != null) {
            System.out.println(o2.toString());
        } else {
            System.out.println("Нет записей с id = " + id);
        }

        dbService1.closeConnection();
        dbService2.closeConnection();

    }
}
