package session.Article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticlePageController {

    @GetMapping("/all-articles")
    public String showAllArticlesPage() {
        return "all-articles";
    }

    @GetMapping("/article/{articleId}")
    public String showArticlePage() {
        return "article";
    }
}
