package ec.edu.espe.arquitectura.inventario.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "products")
@CompoundIndex(name = "idx_clients_name", def = "{'name':1, 'label':1}")
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String uniqueKey;
    private String label;
    private String type;
    private String state;
    private Date creationDate;
    private Date lastModifiedDate;
    private String comments;
    @Version
    private Long version;

    private List<ProductCategory> categories;
}
