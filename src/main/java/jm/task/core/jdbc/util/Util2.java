package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util2 {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/kata?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.setProperty("hibernate.connection.username", USERNAME);
                settings.setProperty("hibernate.connection.password", PASSWORD);
                settings.setProperty("hibernate.connection.url", URL);
                settings.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                settings.setProperty("hibernate.hbm2ddl.auto","create-drop");
                settings.setProperty("hibernate.show_sql", "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

