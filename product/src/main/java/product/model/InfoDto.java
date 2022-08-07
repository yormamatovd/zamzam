package product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDto {
    private Long id;

    private String name;

    private String surname;

    private String photoId;

    private String type;
}
