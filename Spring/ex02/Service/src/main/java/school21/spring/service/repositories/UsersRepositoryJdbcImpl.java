package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource ds;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("hikariDs") DataSource ds) {
        this.ds = ds;
    }

    @Override
    public User findById(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLUser.GET.QUERY)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            User user = null;
            if (rs.next())
                user = new User(rs.getInt(1), rs.getString(2));
            rs.close();
            return user;
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Statement statement = ds.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(SQLUser.GET_ALL.QUERY)) {
            while (rs.next())
                users.add(new User(rs.getInt(1), rs.getString(2)));
        }
        return users;
    }

    @Override
    public void save(User entity) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            pst.setString(1, entity.getEmail());
            pst.executeUpdate();
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            pst.setString(1, entity.getEmail());
            pst.setLong(2, entity.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        return findAll().stream().filter(user -> email.equals(user.getEmail())).findFirst();
    }

    enum SQLUser {
        GET("SELECT * FROM \"user\" WHERE id = ?"),
        GET_ALL("SELECT * FROM \"user\""),
        INSERT("INSERT INTO \"user\" (email) VALUES (?)"),
        UPDATE("UPDATE \"user\" SET email = ? WHERE id = ?"),
        DELETE("DELETE FROM \"user\" WHERE id = ?");

        final String QUERY;

        SQLUser(String query) {
            this.QUERY = query;
        }
    }
}
