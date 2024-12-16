package session.Category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, String> {
    @Modifying
    @Query(value="insert into restaurant_category values (?, ?);", nativeQuery = true)
    void insertCategory(int restaurant_id, String category_id);
    @Query(value="SELECT  category.category_name FROM category\n" + "where category_id IN (SELECT category_id FROM restaurant_category where restaurant_id = ? )", nativeQuery = true)
    List<String> getRestaurantCategory(int restaurant_id);
}