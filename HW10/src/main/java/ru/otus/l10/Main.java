package ru.otus.l10;

import ru.otus.l10.connection.ConnectionHelper;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Executor exec = new Executor(ConnectionHelper.getConnection());

        UserDataSet user1 = new UserDataSet("Гоша", 20);
        UserDataSet user2 = new UserDataSet("Вася", 21);
        UserDataSet user3 = new UserDataSet("Петровна", 60);

        exec.save(user1);
        exec.save(user2);
        exec.save(user3);

        EmployeeDataSet empl1 = new EmployeeDataSet("Маша", 22, 'F');
        EmployeeDataSet empl2 = new EmployeeDataSet("Люся", 23, 'F');
        EmployeeDataSet empl3 = new EmployeeDataSet("Петрович", 65, 'M');

        exec.save(empl1);
        exec.save(empl2);
        exec.save(empl3);


        long id = 3;

        DataSet o1 = exec.load(id, UserDataSet.class);
        if (o1 != null) {
            System.out.println(o1.toString());
        } else {
            System.out.println("Нет записей с id = " + id);
        }

        DataSet o2 = exec.load(id, EmployeeDataSet.class);
        if (o2 != null) {
            System.out.println(o2.toString());
        } else {
            System.out.println("Нет записей с id = " + id);
        }

    }
}
