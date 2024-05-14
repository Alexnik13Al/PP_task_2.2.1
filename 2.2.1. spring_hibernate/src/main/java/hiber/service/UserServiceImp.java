package hiber.service;

import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void add(User user,String carModel, int carSeries) {
        User existingUser = userDao.getUserByAllParams(user.getFirstName(), user.getLastName(),user.getEmail());
        if (existingUser != null && user.equals(existingUser)) {
            System.out.println("Такой пользователь уже существует");
        } else {
            userDao.add(user,carModel,carSeries);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
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
