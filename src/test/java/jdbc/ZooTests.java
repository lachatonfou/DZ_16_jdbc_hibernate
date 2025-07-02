package jdbc;

import jdbc.models.Animal;
import utils.DatabaseUtils;
import jdbc.utils.CRUDUtils;
import utils.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZooTests {
    @BeforeAll
    static void init() {
        DatabaseUtils.createData();
    }

    @AfterAll
    static void tearDown() {
        DatabaseConnection.closeConnection();
    }

    @Test
    void createAnimal() {
        Animal newAnimal = new Animal(11, "Муся", 3, 2, 2, 2);
        int initialCount = CRUDUtils.getAnimalCountRow();
        CRUDUtils.insertAnimalData(newAnimal);
        int newCount = CRUDUtils.getAnimalCountRow();
        assertEquals(initialCount + 1, newCount);

        List<Animal> animals = CRUDUtils.getAnimalData("SELECT * FROM public.animal WHERE Id =" + newAnimal.getId());
        Animal addedAnimal = animals.getLast();
        assertEquals(newAnimal.getId(), addedAnimal.getId());
        assertEquals(newAnimal.getName(), addedAnimal.getName());
        assertEquals(newAnimal.getAge(), addedAnimal.getAge());
        assertEquals(newAnimal.getType(), addedAnimal.getType());
        assertEquals(newAnimal.getSex(), addedAnimal.getSex());
        assertEquals(newAnimal.getPlace(), addedAnimal.getPlace());
    }

    @Test
    void readAnimal() {
        List<Animal> animals = CRUDUtils.getAnimalData("SELECT * FROM public.animal");
        Animal firstAnimal = animals.getFirst();
        assertFalse(animals.isEmpty());
        assertEquals(firstAnimal.getId(), 1);
        assertEquals(firstAnimal.getName(), "Бусинка");
        assertEquals(firstAnimal.getAge(), 2);
        assertEquals(firstAnimal.getType(), 1);
        assertEquals(firstAnimal.getSex(), 1);
        assertEquals(firstAnimal.getPlace(), 1);
    }

    @Test
    void updateAnimal() {
        List<Animal> animals = CRUDUtils.getAnimalData("SELECT * FROM public.animal");
        Animal firstAnimal = animals.getFirst();
        firstAnimal.setName("Пельмешка");
        firstAnimal.setPlace(3);

        assertEquals(firstAnimal.getName(), "Пельмешка");
        assertEquals(firstAnimal.getPlace(), 3);
    }

    @Test
    void deleteAnimal() {
        int initialCount = CRUDUtils.getAnimalCountRow();
        Animal newAnimal = new Animal(11, "Муся", 3, 2, 2, 2);
        CRUDUtils.insertAnimalData(newAnimal);
        int newCount = CRUDUtils.getAnimalCountRow();
        assertEquals(initialCount + 1, newCount);

        CRUDUtils.deleteAnimalById(11);
        int finalCount = CRUDUtils.getAnimalCountRow();
        assertEquals(initialCount, finalCount);

        List<Animal> animals = CRUDUtils.getAnimalData("SELECT * FROM public.animal WHERE Id =" + newAnimal.getId());
        assertTrue(animals.isEmpty());
    }
}
