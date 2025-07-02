package hibernate;

import hibernate.models.Animal;
import hibernate.utils.DBHibernateService;

import jakarta.persistence.Id;
import jakarta.persistence.PersistenceException;
import utils.DatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZooTests {
    DBHibernateService dbHibernateService = new DBHibernateService();

    @BeforeAll
    static void init() {
        DatabaseUtils.createData();
    }

    @Test
    void createAnimal() {
        int initialCount = dbHibernateService.getCountRowAnimal();
        Animal animal = new Animal();
        animal.setId(11);
        animal.setName("Рыжик");
        animal.setAge(4);
        animal.setType(2);
        animal.setSex(1);
        animal.setPlace(3);
        dbHibernateService.insertAnimal(animal);

        int newCount = dbHibernateService.getCountRowAnimal();
        assertEquals(initialCount + 1, newCount);
    }

    @Test
    void countRowAnimal() {
        Assertions.assertEquals(10, dbHibernateService.getCountRowAnimal());
    }

    static Stream<Animal> animalProvider() {
        List<Animal> animals = new ArrayList<>();
        for (int id = 1; id <= 10; id++) {
            Animal animal = new Animal();
            animal.setId(id);
            animal.setName("Sharik");
            animal.setAge(10);
            animal.setType(1);
            animal.setSex(1);
            animal.setPlace(1);
            animals.add(animal);
        }
        return animals.stream();
    }
    /**
     * В таблицу public.animal нельзя добавить строку с индексом от 1 до 10 включительно
     */
    @ParameterizedTest
    @MethodSource("animalProvider")
    void insertIndexAnimal(Animal animal) {
        assertThrows(PersistenceException.class, () -> dbHibernateService.insertAnimal(animal));
    }


}
