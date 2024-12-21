package session.Article;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import session.Booking.DTO.bookTableDTO;
import session.Booking.DTO.createDecisionDTO;
import session.Restaurant.DTO.RestaurantDTO;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles")
    public List<Article> getArticles() {
        List<Article> allArticles = articleRepository.findAll();
        Collections.shuffle(allArticles);  // Shuffle the list to get random articles
        return allArticles.size() > 3 ? allArticles.subList(0, 3) : allArticles;  // Get top 3 or fewer
    }

    @GetMapping("/article/{articleId}")
    public Article getArticleById(@PathVariable int articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    @GetMapping("/all-articles")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<Object> deleteArticle(@PathVariable int articleId) {
        articleRepository.deleteById(articleId);
        return ResponseEntity.ok().build();
    }
    @Transactional
    @PostMapping(value="/article",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> saveArticle(@ModelAttribute() ArticleDTO articleDTO) {
        articleRepository.save(ArticleDTO.toEntity(articleDTO));
        return ResponseEntity.ok().build();
    }
    @Transactional
    @PostMapping(value = "/article/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> updateRestaurant(
            HttpSession session,
            @ModelAttribute ArticleDTO articleDTO // For FORM data
    ) {
        articleRepository.save(ArticleDTO.toEntity(articleDTO,articleDTO.getArticleId()));
        return ResponseEntity.ok("Decision updated successfully.");
    }
}