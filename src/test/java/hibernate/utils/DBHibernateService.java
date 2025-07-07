package hibernate.utils;

import hibernate.models.Animal;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DBHibernateService {
    private final Session session;

    public DBHibernateService(Session session) {
        this.session = session;
    }

    public void insertAnimal(Animal animal) {
        session.beginTransaction();
        session.persist(animal);
        //session.createNativeQuery(String.format(INSERT_ANIMAL, animal.getId(), animal.getName(), animal.getAge(), animal.getType(), animal.getSex(), animal.getPlace())).executeUpdate();
        session.getTransaction().commit();
    }
    public List<hibernate.models.Animal> getAnimalData() {
        session.beginTransaction();
        List<hibernate.models.Animal> animals = session.createQuery("FROM Animal", Animal.class).getResultList();

        session.getTransaction().commit();
        return animals;
    }

    public void deleteAnimal(Animal animal){
        session.beginTransaction();
        session.delete(animal);
        session.getTransaction().commit();
    }

    public int getCountRowAnimal() {
        session.beginTransaction();
        int count = session.createNativeQuery("SELECT * from animal ", Animal.class).getResultList().size();
        System.out.printf("Table public.animal has exact %s rows%n", count);
        session.getTransaction().commit();
        return count;
    }
}
