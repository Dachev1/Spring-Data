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
//        entityManager.doAlter(User.class);

        User dachev = new User("Dachev", 19, LocalDate.now(), "petq@gmail.com");
        entityManager.persist(dachev);
    }
}
