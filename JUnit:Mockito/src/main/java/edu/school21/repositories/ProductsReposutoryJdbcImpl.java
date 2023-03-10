package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsReposutoryJdbcImpl implements ProductsRepository {
    private final DataSource ds;

    public ProductsReposutoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        try (Connection connection = ds.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQLMessage.SELECT.QUERY);
            List<Product> productAll = new LinkedList<>();
            while (rs.next()) {
                productAll.add(new Product(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price")));
            }
            rs.close();
            return productAll;
        }
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLMessage.GET.QUERY)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            Product product = null;
            if (rs.next()) {
                product = new Product(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"));
            }
            rs.close();
            return Optional.ofNullable(product);
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLMessage.UPDATE.QUERY)) {
            pst.setString(1, product.getName());
            pst.setInt(2, product.getPrice());
            pst.setLong(3, product.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLMessage.INSERT.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, product.getName());
            pst.setInt(2, product.getPrice());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next())
                product.setId(rs.getLong(1));
            rs.close();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLMessage.DELETE.QUERY)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        }
    }

    enum SQLMessage {
        SELECT("SELECT * FROM product"),
        GET("SELECT * FROM product WHERE id = ?"),
        UPDATE("UPDATE product SET name = ?, price = ? WHERE id = ?"),
        INSERT("INSERT INTO product (name, price) VALUES (?, ?)"),
        DELETE("DELETE FROM product WHERE id = ?");

        final String QUERY;

        SQLMessage(String query) {
            this.QUERY = query;
        }
    }
}
