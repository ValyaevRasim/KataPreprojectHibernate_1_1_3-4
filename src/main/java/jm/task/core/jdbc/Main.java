package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserServiceImpl dao = new UserServiceImpl();

        User user1 = new User("name1","lastName1", (byte) 11);
        User user2 = new User("name2","lastName2", (byte) 12);
        User user3 = new User("name3","lastName3", (byte) 13);
        User user4 = new User("name4","lastName4", (byte) 14);

        // 1- Создание таблицы User(ов)
        dao.createUsersTable();

        // 2 - Добавление 4 User(ов) в таблицу с данными на свой выбор.
        dao.saveUser(user1.getName(),user1.getLastName(),user1.getAge());
        System.out.printf("User с именем – %s добавлен в базу данных\n",user1.getName());


        dao.saveUser(user2.getName(),user2.getLastName(),user2.getAge());
        System.out.printf("User с именем – %s добавлен в базу данных\n",user2.getName());

        dao.saveUser(user3.getName(),user3.getLastName(),user3.getAge());
        System.out.printf("User с именем – %s добавлен в базу данных\n",user3.getName());

        dao.saveUser(user4.getName(),user4.getLastName(),user4.getAge());
        System.out.printf("User с именем – %s добавлен в базу данных\n",user4.getName());

        // 3- Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> list = dao.getAllUsers();
        for (User l:list) {
            System.out.println(l.toString());
        }

        dao.removeUserById(1);

//         4- Очистка таблицы User(ов)
        dao.cleanUsersTable();

        dao.dropUsersTable();

    }
}
