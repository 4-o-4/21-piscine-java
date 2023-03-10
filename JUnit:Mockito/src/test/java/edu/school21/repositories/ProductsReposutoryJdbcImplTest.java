package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductsReposutoryJdbcImplTest {
    ProductsReposutoryJdbcImpl repository;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = List.of(
            new Product(0L, "A", 121),
            new Product(1L, "B", 231),
            new Product(2L, "C", 343),
            new Product(3L, "D", 278),
            new Product(4L, "E", 993));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "C", 343);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "CCC", 343);

    @BeforeEach
    void init() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .addScripts("schema.sql", "data.sql")
                .build();
        repository = new ProductsReposutoryJdbcImpl(dataSource);
    }

    @Test
    void findAll() throws SQLException {
        List<Product> products = repository.findAll();
        for (int i = 0; i < EXPECTED_FIND_ALL_PRODUCTS.size(); i++)
            assertEquals(products.get(i), EXPECTED_FIND_ALL_PRODUCTS.get(i));
    }

    @Test
    void findById() throws SQLException {
        Optional<Product> productOptional = repository.findById(2L);
        assertEquals(productOptional.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    void update() throws SQLException {
        repository.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> productOptional = repository.findById(EXPECTED_UPDATED_PRODUCT.getId());
        assertEquals(productOptional.get(), EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    void save() throws SQLException {
        repository.save(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> productOptional = repository.findById(5L);
        assertEquals(productOptional.get(), EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    void delete() throws SQLException {
        repository.delete(0L);
        Optional<Product> productOptional = repository.findById(0L);
        assertFalse(productOptional.isPresent());
    }
}
