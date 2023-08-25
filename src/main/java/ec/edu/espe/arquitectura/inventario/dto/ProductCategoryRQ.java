package ec.edu.espe.arquitectura.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryRQ {
    private String categoryName;
    private Integer units;
    private String state;
}
