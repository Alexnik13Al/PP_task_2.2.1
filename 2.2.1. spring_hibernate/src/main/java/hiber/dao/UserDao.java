package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
    void add(User user, String carModel, int carSeries);

    List<User> listUsers();

    User getUserByAllParams(String firstName, String lastName, String email);

    User getUserByCarModelAndSeries(String model, int series);
}
