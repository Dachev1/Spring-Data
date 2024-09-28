import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector connector = new MyConnector("root", "root", "mini_orm_db");

        EntityManager<User> entityManager = new EntityManager<>(connector.getConnection());

//        User user1 = new User("user1", 19, LocalDate.of(2021, 5, 1));
//        User user2 = new User("user2", 32, LocalDate.of(2019, 3, 15));
//        User user3 = new User("user3", 14, LocalDate.of(2022, 7, 10));
//        User user4 = new User("user4", 21, LocalDate.of(2020, 12, 10));
//        User user5 = new User("user5", 71, LocalDate.of(2020, 12, 10));

//        entityManager.persist(user1);
//        entityManager.persist(user2);
//        entityManager.persist(user3);
//        entityManager.persist(user4);
//        entityManager.persist(user5);

        Iterable<User> users = entityManager.find(User.class, "registration > 2020 AND age > 20");
        for (User user : users) {
            System.out.println(user.getUsername());
        }
    }
}
