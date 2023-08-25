package ec.edu.espe.arquitectura.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryRS {
    private String categoryName;
    private Integer units;
    private String state;
}
