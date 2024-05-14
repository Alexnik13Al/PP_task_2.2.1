package hiber;

import hiber.config.AppConfig;
import hiber.dao.UserDaoImp;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"),"BMW",2020);
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"),"Audi", 2021);
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"),"Chevrolet",2023);
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"),"Lamborgini",2024);



      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("User ID = " + user.getId());
         System.out.println("User First Name = " + user.getFirstName());
         System.out.println("User Last Name = " + user.getLastName());
         System.out.println("User Email = " + user.getEmail());
         System.out.println("User Car ID = " + user.getCar().getId());
         System.out.println("User Car Model = " + user.getCar().getModel());
         System.out.println("User Car Series = " + user.getCar().getSeries());
         System.out.println();
      }

      User user = userService.getUserByCarModelAndSeries("BMW", 2020);

      System.out.println("User ID = " + user.getId());
      System.out.println("User First Name = " + user.getFirstName());
      System.out.println("User Last Name = " + user.getLastName());
      System.out.println("User Email = " + user.getEmail());
      System.out.println("User Car ID = " + user.getCar().getId());
      System.out.println("User Car Model = " + user.getCar().getModel());
      System.out.println("User Car Series = " + user.getCar().getSeries());

      context.close();
   }
}
