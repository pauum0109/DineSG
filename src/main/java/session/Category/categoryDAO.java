package session.Category;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class categoryDAO {
    private final JdbcTemplate jdbcTemplate;

    public categoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<String>> getRestaurantCategory(int category_id) {
        try {
            String query = "SELECT  category.category_name FROM category\n" + "where category_id IN (SELECT category_id FROM restaurant_category where restaurant_id = ? )";
            return Optional.ofNullable(jdbcTemplate.queryForList(query, String.class, category_id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}