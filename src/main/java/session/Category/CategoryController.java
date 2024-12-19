package session.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryRepo categoryRepo;
    public CategoryController(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    @GetMapping("/getCategory")
    public List<Category> getCategory() {
        return categoryRepo.findAll();
    }
    @GetMapping("/getRestaurantCategory")
    public List<String> getRestaurantCategory(@RequestParam int restaurant_id) {
        return categoryRepo.getRestaurantCategory(restaurant_id);
    }
    @PostMapping("/addCategory")
    public void addCategory(Category category) {
        categoryRepo.save(category);
    }
    @PostMapping("/removeCategory")
    public void removeCategory(String id) {
        categoryRepo.deleteById(id);
    }
}