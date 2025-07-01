package jdbc;

import jdbc.models.Animal;
import jdbc.utils.DatabaseUtils;
import jdbc.utils.CRUDUtils;
import jdbc.models.Places;
import jdbc.utils.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
