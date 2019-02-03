package ru.otus.l11.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.l11.dataSets.AddressDataSet;
import ru.otus.l11.dataSets.PhoneDataSet;
import ru.otus.l11.dataSets.UserDataSet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";   //"jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public static Connection getJDBCConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection (DB_URL, USER,PASS);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getHibernateSessionFactory(String pckName) {
        Configuration configuration = new Configuration();

        try {
            for (Class c: ReflectionHelper.getClasses(pckName)) {
                configuration.addAnnotatedClass(c);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


//        configuration.addPackage(pckName);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", JDBC_DRIVER);
        configuration.setProperty("hibernate.connection.url", DB_URL);
        configuration.setProperty("hibernate.connection.username", USER);
        configuration.setProperty("hibernate.connection.password", PASS);
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
//        configuration.setProperty("hibernate.current_session_context_class", "thread");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
