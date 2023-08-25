package ec.edu.espe.arquitectura.inventario.dto;

import ec.edu.espe.arquitectura.inventario.model.ProductCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRQ {
    private String name;
    private String label;
    private String type;
    private String state;
    private String comments;
}
