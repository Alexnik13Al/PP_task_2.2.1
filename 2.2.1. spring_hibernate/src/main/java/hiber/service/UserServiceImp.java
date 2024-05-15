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

    @Override
    public User getUserByCarModelAndSeries(String carModel, int carSeries) {
        return userDao.getUserByCarModelAndSeries(carModel, carSeries);
    }
}
