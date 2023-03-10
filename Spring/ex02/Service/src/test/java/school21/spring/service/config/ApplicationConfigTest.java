package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfigTest {
    @Bean
    DataSource hsqldb() {
        return new EmbeddedDatabaseBuilder()
                .addScript("schema.sql")
                .build();
    }

    @Bean
    UsersRepository usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl(hsqldb());
    }

    @Bean
    UsersService usersService() {
        return new UsersServiceImpl(usersRepositoryJdbcTemplate());
    }
}
