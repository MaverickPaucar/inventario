package ec.edu.espe.arquitectura.inventario.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProductRS {
    private String name;
    private String uniqueKey;
    private String label;
    private String type;
    private String state;
    private Date creationDate;
    private Date lastModifiedDate;
    private String comments;
    private List<ProductCategoryRS> categories;
}
