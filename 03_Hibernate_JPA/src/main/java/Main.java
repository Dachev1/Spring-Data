import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static EntityManager em;

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni");
        em = emf.createEntityManager();

        em.getTransaction().begin();
        // ex_2(), ex_3(), ex_4(), ex_5(), ex_6(), ex_7(), ex_8(), ex_9(), ex_10(), ex_11(), ex_12(), ex_13()
        em.getTransaction().commit();
    }

    private static void ex_13() throws IOException {
        String townName = READER.readLine();

        List<Address> addressesList = em.createQuery(
                        "SELECT a FROM Address a WHERE a.town.name = :town_name", Address.class)
                .setParameter("town_name", townName)
                .getResultList();

        List<Employee> employees = em.createQuery(
                        "SELECT e FROM Employee e WHERE e.address IN :addresses", Employee.class)
                .setParameter("addresses", addressesList)
                .getResultList();

        employees.forEach(e -> {
            e.setAddress(null);
            em.persist(e);
        });

        addressesList.forEach(em::remove);

        if (!addressesList.isEmpty()) {
            Town townToRemove = addressesList.get(0).getTown();
            em.remove(townToRemove);
        }

        System.out.printf("%d address(es) in %s deleted", addressesList.size(), townName);
    }

    private static void ex_12() {
        // Corrected JPQL Query
        List<Object[]> results = em.createQuery(
                        "SELECT d.name, MAX(e.salary) " +
                                "FROM Department d JOIN d.employees e " +
                                "GROUP BY d.name " +
                                "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000")
                .getResultList();

// Processing the result
        results.forEach(result -> {
            String departmentName = (String) result[0];
            BigDecimal maxSalary = (BigDecimal) result[1];  // Use BigDecimal here

            System.out.printf("%s %.2f%n", departmentName, maxSalary.doubleValue());  // Convert to double for printing
        });
    }

    private static void ex_11() throws IOException {
        em.createQuery("SELECT e FROM Employee e " +
                                "WHERE e.firstName LIKE CONCAT(:letters, '%') ORDER BY e.salary DESC",
                        Employee.class)
                .setParameter("letters", READER.readLine())
                .getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }

    private static void ex_10() {
        em.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(new BigDecimal("1.12")));
                    em.persist(e);

                    System.out.printf("%s %s (%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary());
                });
    }

    private static void ex_9() {
        em.createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream().sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s%n", p.getName());
                    System.out.printf("     Project Description: %s%n", p.getDescription());
                    System.out.printf("     Project Start Date: %s%n", p.getStartDate());
                    System.out.printf("     Project END Date: %s%n", p.getEndDate());
                });
    }

    private static void ex_8() throws IOException {
        em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class)
                .setParameter("id", Integer.parseInt(READER.readLine()))
                .getResultList()
                .forEach(e -> {
                    System.out.printf("%s %s - %s%n", e.getFirstName(), e.getLastName(), e.getJobTitle());
                    e.getProjects()
                            .stream()
                            .sorted(Comparator.comparing(Project::getName))
                            .forEach(p -> System.out.printf("   %s%n", p.getName()));
                });
    }

    private static void ex_7() {
        em.createQuery("SELECT a FROM Address a ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> System.out.printf("%s, %s - %d employeesn%n",
                        a.getText(), a.getTown().getName(), a.getEmployees().size()));
    }

    private static void ex_6() throws IOException {
        Town town = em.find(Town.class, 32);

        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);

        em.persist(address);

        em.createQuery("SELECT e FROM Employee e WHERE e.lastName = :last_name", Employee.class)
                .setParameter("last_name", READER.readLine())
                .getResultList()
                .forEach(e -> e.setAddress(address));
    }

    private static void ex_5() {
        em.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = 'Research and Development' ORDER BY e.salary, e.id", Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s from Research and Development - $%.2f%n",
                        e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    private static void ex_4() {
        em.createQuery("SELECT e FROM Employee e WHERE e.salary > 50000", Employee.class)
                .getResultList()
                .forEach(employee -> System.out.println(employee.getFirstName()));
    }

    private static void ex_3() throws IOException {
        String[] fullName = READER.readLine().split("\\s+");

        List<Employee> resultList = em.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.firstName = :first_name AND e.lastName = :last_name", Employee.class)
                .setParameter("first_name", fullName[0])
                .setParameter("last_name", fullName[1])
                .getResultList();

        if (resultList.isEmpty()) {
            System.out.println("No employees found");
        } else {
            System.out.printf("Found %s %s employees", fullName[0], fullName[1]);
        }
    }

    private static void ex_2() {
        em.createQuery("SELECT t FROM Town t WHERE LENGTH(t.name) > 5", Town.class)
                .getResultList()
                .forEach(t -> {
                    t.setName(t.getName().toUpperCase());
                    em.persist(t);
                });
    }
}
