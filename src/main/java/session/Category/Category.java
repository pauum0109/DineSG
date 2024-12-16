package session.Category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = "category_name"))
public class Category  {
    @Id
    @Column(name = "category_id", length = 20, nullable = false)
    private String category_id;
    @Column(name = "category_name", length = 20, nullable = false, unique = true)
    private String category_name;
    @Column(name = "category_image", length = 20)
    private String category_image;
}