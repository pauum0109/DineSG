package session.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Collections;

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

}
