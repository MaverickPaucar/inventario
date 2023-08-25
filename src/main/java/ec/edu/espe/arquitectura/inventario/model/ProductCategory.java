package ec.edu.espe.arquitectura.inventario.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategory {
    private String categoryName;
    private Integer units;
    private String state;
}
