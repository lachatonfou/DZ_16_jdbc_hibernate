package hibernate.utils;

import hibernate.models.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DBImprovedHibernateService {
    private static final SessionFactory sessionFactory = SessionFactoryCreator.createSessionFactory();

    private Session openSession() {
        return sessionFactory.openSession();
    }

    public Animal getAnimalByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM Animal WHERE name = :name", Animal.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    public int getAnimalCount() {
        try (Session session = openSession()) {
            Long count = session.createQuery("SELECT COUNT(a) FROM Animal a", Long.class)
                    .getSingleResult();
            System.out.printf("Table public.animal has exact %s rows%n", count);
            return count.intValue();
        }
    }

    public void insertAnimal(Animal animal) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(animal);
            tx.commit();
        }
    }
}
