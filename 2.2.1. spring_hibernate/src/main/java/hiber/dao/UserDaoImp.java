package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user, String carModel, int carSeries) {
        Car car = new Car();
        car.setModel(carModel);
        car.setSeries(carSeries);
        sessionFactory.getCurrentSession().save(car);
        user.setCar(car);
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByAllParams(String firstName, String lastName, String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE firstName = :firstName AND lastName = :lastName AND email = :email");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("email", email);

        try {
            return (User) query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        String hql = "SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series);

        try {
            User user = query.getSingleResult();
            return user != null ? user : new User();
        } catch (NonUniqueResultException | NoResultException e) {
            return new User();
        }
    }


}
