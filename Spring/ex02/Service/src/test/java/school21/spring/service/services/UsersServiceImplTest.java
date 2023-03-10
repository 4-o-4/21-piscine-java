package school21.spring.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfigTest;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {
    private UsersRepository usersRepository;
    private UsersService usersService;

    @BeforeEach
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigTest.class);
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersService = context.getBean("usersService", UsersService.class);
    }

    @Test
    public void isSignUp() throws SQLException {
        assertNotNull(usersService.signUp("test@test.com"));
    }

    @Test
    public void isFindByEmail() throws SQLException {
        String email = "test@test.com";
        usersService.signUp(email);
        Optional<User> user = usersRepository.findByEmail(email);
        assertEquals(email, user.get().getEmail());
    }
}
