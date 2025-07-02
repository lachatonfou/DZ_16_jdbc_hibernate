package hibernate.utils;

import hibernate.models.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DBHibernateService {
    private static final String INSERT_ANIMAL = "INSERT INTO animal (id, \"name\", age, \"type\", sex, place) VALUES (%s, '%s', %s, %s, %s, %s)";
    private static final String DELETE_ANIMAL = "DELETE FROM public.animal WHERE id = ?";

    public Animal getAnimalByName() {
        SessionFactory sessionFactory = SessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();

        return session.createNativeQuery("SELECT id, \"name\", age, \"type\", sex, place FROM animal WHERE \"name\" = 'Пчелка'", Animal.class).getResultList().get(0);
    }

    public void insertAnimal(Animal animal) {
        SessionFactory sessionFactory = SessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(String.format(INSERT_ANIMAL, animal.getId(), animal.getName(), animal.getAge(), animal.getType(), animal.getSex(), animal.getPlace())).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAnimalById(int Id){

    }

    public int getCountRowAnimal() {
        SessionFactory sessionFactory = SessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        int count = session.createNativeQuery("SELECT * from animal ", Animal.class).getResultList().size();
        System.out.printf("Table public.animal has exact %s rows%n", count);
        return count;
    }
}
