package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.sql.SQLException;

public class Main {
    public static long n = 4;

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);

        for (int i = 0; i < 5; i++)
            usersRepository.save(new User(i + "@hikari.com"));
        usersRepository.findAll().forEach(System.out::println);
        User user = usersRepository.findById(n);
        user.setEmail(n + "@hikari-update.com");
        usersRepository.update(user);
        System.out.printf("\n-----\n%s\n-----\n\n", usersRepository.findByEmail(user.getEmail()));

        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersRepository.findAll().forEach(System.out::println);
        user = usersRepository.findById(n);
        user.setEmail(n + "@update.com");
        usersRepository.update(user);
        System.out.printf("\n-----\n%s\n-----\n\n", usersRepository.findByEmail(user.getEmail()));
        for (int i = 0; i < 5; i++)
            usersRepository.save(new User(i + "@spring.com"));
    }
}
