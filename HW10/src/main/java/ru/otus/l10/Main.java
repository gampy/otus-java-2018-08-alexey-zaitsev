package ru.otus.l10;

import ru.otus.l10.connection.ConnectionHelper;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Executor exec = new Executor(ConnectionHelper.getConnection());
        exec.prepareTables();

        UserDataSet user1 = new UserDataSet("Гоша", 20);
        UserDataSet user2 = new UserDataSet("Вася", 21);
        UserDataSet user3 = new UserDataSet("Петрович", 60);

        exec.save(user1);
        exec.save(user2);
        exec.save(user3);

        long id = 3;
        UserDataSet user = exec.load(id, UserDataSet.class);
        if (user != null) {
            System.out.println(user.toString());
        } else {
            System.out.println("Нет записей с id = " + id);
        }
    }
}
