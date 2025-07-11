package hibernate;

import hibernate.models.Animal;
import hibernate.utils.DBHibernateService;

import hibernate.utils.SessionFactoryCreator;
import org.assertj.core.api.SoftAssertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import utils.DatabaseConnection;
import utils.DatabaseUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZooTests {
    private static SessionFactory sessionFactory;
    private Session session;
    private DBHibernateService dbHibernateService;
//    DBHibernateService dbHibernateService = new DBHibernateService();
//    SessionFactory sessionFactory = SessionFactoryCreator.createSessionFactory();
//    Session session = sessionFactory.openSession();

    @BeforeAll
    static void init() {
        sessionFactory = SessionFactoryCreator.createSessionFactory();
        DatabaseUtils.createData();
    }

    @BeforeEach
    void setup() {
        session = sessionFactory.openSession();
        dbHibernateService = new DBHibernateService(session);
    }

    @AfterEach
    void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        DatabaseConnection.closeConnection();
    }

    @Test
    void createAnimal() {
        int initialCount = dbHibernateService.getCountRowAnimal();
        Animal newAnimal = new Animal(11, "Рыжик", 4, 2, 1, 3);
        dbHibernateService.insertAnimal(newAnimal);

        int newCount = dbHibernateService.getCountRowAnimal();
        assertEquals(initialCount + 1, newCount);

        List<hibernate.models.Animal> animals = dbHibernateService.getAnimalData();

        hibernate.models.Animal addedAnimal = animals.getLast();
//        assertEquals(newAnimal.getId(), addedAnimal.getId());
//        assertEquals(newAnimal.getName(), addedAnimal.getName());
//        assertEquals(newAnimal.getAge(), addedAnimal.getAge());
//        assertEquals(newAnimal.getType(), addedAnimal.getType());
//        assertEquals(newAnimal.getSex(), addedAnimal.getSex());
//        assertEquals(newAnimal.getPlace(), addedAnimal.getPlace());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(newAnimal.getId()).isEqualTo(addedAnimal.getId());
        softly.assertThat(newAnimal.getName()).isEqualTo(addedAnimal.getName());
        softly.assertThat(newAnimal.getAge()).isEqualTo(addedAnimal.getAge());
        softly.assertThat(newAnimal.getType()).isEqualTo(addedAnimal.getType());
        softly.assertThat(newAnimal.getSex()).isEqualTo(addedAnimal.getSex());
        softly.assertThat(newAnimal.getPlace()).isEqualTo(addedAnimal.getPlace());
        softly.assertAll();
    }

    @Test
    void readAnimal() {
        List<hibernate.models.Animal> animals = dbHibernateService.getAnimalData();
        hibernate.models.Animal firstAnimal = animals.getFirst();
        assertFalse(animals.isEmpty());
//        assertEquals(firstAnimal.getId(), 1);
//        assertEquals(firstAnimal.getName(), "Бусинка");
//        assertEquals(firstAnimal.getAge(), 2);
//        assertEquals(firstAnimal.getType(), 1);
//        assertEquals(firstAnimal.getSex(), 1);
//        assertEquals(firstAnimal.getPlace(), 1);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(firstAnimal.getId()).isEqualTo(1);
        softly.assertThat(firstAnimal.getName()).isEqualTo("Бусинка");
        softly.assertThat(firstAnimal.getAge()).isEqualTo(2);
        softly.assertThat(firstAnimal.getType()).isEqualTo(1);
        softly.assertThat(firstAnimal.getSex()).isEqualTo(1);
        softly.assertThat(firstAnimal.getPlace()).isEqualTo(1);
        softly.assertAll();
    }

    @Test
    void updateAnimal() {
        List<hibernate.models.Animal> animals = dbHibernateService.getAnimalData();
        hibernate.models.Animal firstAnimal = animals.getFirst();
        firstAnimal.setName("Пельмешка");
        firstAnimal.setPlace(3);

        assertEquals(firstAnimal.getName(), "Пельмешка");
        assertEquals(firstAnimal.getPlace(), 3);
    }

    @Test
    void deleteAnimal() {
        int initialCount = dbHibernateService.getCountRowAnimal();
        Animal newAnimal = new Animal(11, "Рыжик", 4, 2, 1, 3);
        dbHibernateService.insertAnimal(newAnimal);

        int newCount = dbHibernateService.getCountRowAnimal();
        assertEquals(initialCount + 1, newCount);

        dbHibernateService.deleteAnimal(newAnimal);
        int finalCount = dbHibernateService.getCountRowAnimal();
        assertEquals(initialCount, finalCount);
    }
}
