package ec.edu.espe.arquitectura.inventario.repository;

import ec.edu.espe.arquitectura.inventario.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findFirstByUniqueKey(String uniqueKey);
    List<Product> findByNameLike(String name);
}
