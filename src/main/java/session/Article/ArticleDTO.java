package session.Article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
public class ArticleDTO {
    private int articleId;
    private String title;
    private String content;
    private String image;
    private String updatedAt;
    public static Article toEntity(ArticleDTO articleDTO){
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setImage(articleDTO.getImage());
        return article;
    }
    public static Article toEntity(ArticleDTO articleDTO,int id){
        Article article = new Article();
        article.setArticleId(id);
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setImage(articleDTO.getImage());
        article.setUpdatedAt(Instant.now());
        return article;
    }
    public static ArticleDTO toDTO(Article article){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(article.getArticleId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setImage(article.getImage());
        articleDTO.setUpdatedAt(article.getUpdatedAt().toString());
        return articleDTO;
    }

}