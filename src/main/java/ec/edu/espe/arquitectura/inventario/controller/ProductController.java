package ec.edu.espe.arquitectura.inventario.controller;

import ec.edu.espe.arquitectura.inventario.dto.ProductRQ;
import ec.edu.espe.arquitectura.inventario.dto.ProductRS;
import ec.edu.espe.arquitectura.inventario.model.Product;
import ec.edu.espe.arquitectura.inventario.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://banquito-ws-client-production.up.railway.app/doc/swagger-ui/index.html")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductRS> obtainByUniqueKey(@PathVariable(name = "uuid") String uuid){
        try{
            ProductRS product = this.productService.obtainProductByUniqueKey(uuid);
            return ResponseEntity.ok(product);
        } catch (RuntimeException rte){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> productCreate(@RequestBody ProductRQ product){
        try {
            Product productRS = this.productService.productCreate(product);
            return ResponseEntity.ok(productRS);
        } catch (RuntimeException rte){
            return ResponseEntity.badRequest().build();
        }
    }
}
