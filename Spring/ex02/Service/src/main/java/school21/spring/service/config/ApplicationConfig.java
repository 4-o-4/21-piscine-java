package school21.spring.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("school21.spring.service")
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.url}")
    private String url;

    @Value("${db.driver.name}")
    private String driver;

    @Bean()
    DataSource driverManagerDs() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(this.url);
        dataSource.setDriverClassName(this.driver);
        return dataSource;
    }

    @Bean
    DataSource hikariDs() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(this.url);
        return dataSource;
    }
}
