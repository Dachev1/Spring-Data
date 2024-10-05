//import lab.relations.entities.Driver;
//import lab.relations.entities.Truck;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//import java.math.BigDecimal;
//
//public class Demo {
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("main_lab");
//
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//
////        PlateNumber plateNumber = new PlateNumber("X1111AM");
////        Car car = new Car("BMW X6", BigDecimal.valueOf(45000), "GAS", 5, plateNumber);
//
//        //       Company company = new Company("BuzzAir");
//        //      Plane plane1 = new Plane("SKR1125", BigDecimal.valueOf(500000), "Diesel", 100, "SOUFT", company);
//        //     Plane plane2 = new Plane("BOING77", BigDecimal.valueOf(1000000), "Diesel", 200, "BGWIZZ", company);
//
//        Driver driver = new Driver("Ivan Dachev");
//        Truck truck1 = new Truck("VOLVO", BigDecimal.ZERO, "DIESEL", 100000.0);
//        Truck truck2 = new Truck("SCANIA", BigDecimal.TEN, "DIESEL", 2000.0);
//
//        driver.getTrucks().add(truck1);
//        driver.getTrucks().add(truck2);
//        truck1.getDrivers().add(driver);
//        truck2.getDrivers().add(driver);
//
//        em.persist(driver);
//        em.persist(truck1);
//        em.persist(truck2);
//
//        em.getTransaction().commit();
//        emf.close();
//    }
//
//    /* @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//    The single table strategy is one of the most simplest and
//    efficient way to define the implementation of inheritance.
//    In this approach, instances of the multiple entity classes are stored as attributes in a single table only.
//    Here the abstract class needs to have @Table(name = ""name of the table) and every child class need to have only @Entity*/
//
//    /* @Inheritance(strategy=InheritanceType.JOINED)
//    In joined strategy, a separate table is generated for every entity class.
//    The attribute of each table is joined with the primary key.
//    It removes the possibility of duplicacy.
//     Here the abstract class needs to have @Table(name = ""name of the table) and every child class need to have too */
//
//    /* @Inheritence(strategy = InheritanceType.TABLE_PER_CLASS)
//    While @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) mirrors @MappedSuperclass in terms of
//    mapping each subclass and its superclass to a single table, it offers a crucial advantage:
//    It preserves polymorphism within the application domain.
//    This means you can seamlessly load all records that conform to the superclass, even if they belong to different subclasses. */
//}
