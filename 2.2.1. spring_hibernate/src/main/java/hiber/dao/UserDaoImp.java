package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

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


}
