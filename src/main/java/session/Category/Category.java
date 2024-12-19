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
    @Column(name = "category_image", length = 200)
    private String category_image;

    public String getCategoryId() {
        return category_id;
    }

    public void setCategoryId(String category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    public String getImagelink() {
        return category_image;
    }

    public void setImagelink(String category_image) {
        this.category_image = category_image;
    }
}