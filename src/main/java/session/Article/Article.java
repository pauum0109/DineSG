package session.Article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "article")
public class Article {
    // Getters and Setters
    @Id
    private int articleId;
    private String title;
    private String content;
    private String image;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "Updated_at")
    private Instant updatedAt;

}