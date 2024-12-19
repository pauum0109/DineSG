package session.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import session.Category.Category;
import session.Category.CategoryRepo;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
