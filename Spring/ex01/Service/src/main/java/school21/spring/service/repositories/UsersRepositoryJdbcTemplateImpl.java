package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import school21.spring.service.models.User;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(SQLUser.GET.QUERY, params, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQLUser.GET_ALL.QUERY, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
        jdbcTemplate.update(SQLUser.INSERT.QUERY, params);
    }

    @Override
    public void update(User entity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
        jdbcTemplate.update(SQLUser.UPDATE.QUERY, params);
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(SQLUser.DELETE.QUERY, params);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream().filter(user -> email.equals(user.getEmail())).findFirst();
    }

    enum SQLUser {
        GET("SELECT * FROM \"user\" WHERE id=:id"),
        GET_ALL("SELECT * FROM \"user\""),
        INSERT("INSERT INTO \"user\" (email) VALUES (:email)"),
        UPDATE("UPDATE \"user\" SET email=:email WHERE id=:id"),
        DELETE("DELETE FROM \"user\" WHERE id=:id");

        final String QUERY;

        SQLUser(String query) {
            this.QUERY = query;
        }
    }
}
