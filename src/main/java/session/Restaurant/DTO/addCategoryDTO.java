package session.Restaurant.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class addCategoryDTO {
    private int restaurant_id;
    private List<String> category_id;

}