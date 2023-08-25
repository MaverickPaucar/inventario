package ec.edu.espe.arquitectura.inventario.service;

import ec.edu.espe.arquitectura.inventario.dto.ProductCategoryRQ;
import ec.edu.espe.arquitectura.inventario.dto.ProductCategoryRS;
import ec.edu.espe.arquitectura.inventario.dto.ProductRQ;
import ec.edu.espe.arquitectura.inventario.dto.ProductRS;
import ec.edu.espe.arquitectura.inventario.model.Product;
import ec.edu.espe.arquitectura.inventario.model.ProductCategory;
import ec.edu.espe.arquitectura.inventario.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Product transformProductRQ(ProductRQ rq) {
        Product product = Product.builder()
                .name(rq.getName())
                .label(rq.getLabel())
                .type(rq.getType())
                .comments(rq.getComments())
                .build();
        return product;
    }
    private ProductRS transformProductRS(Product product){
        List<ProductCategoryRS> productCategoriesRS = this.transformCategoriesRS(product.getCategories());
        ProductRS rs = ProductRS
                .builder()
                .name(product.getName())
                .uniqueKey(product.getUniqueKey())
                .label(product.getLabel())
                .type(product.getType())
                .state(product.getState())
                .creationDate(product.getCreationDate())
                .lastModifiedDate(product.getLastModifiedDate())
                .comments(product.getComments())
                .categories(productCategoriesRS)
                .build();
        return rs;
    }
    private List<ProductCategory> transformProductCategoriesRQ(List<ProductCategoryRQ> rq) {
        List<ProductCategory> productCategories = new ArrayList<>();
        for (ProductCategoryRQ productCategoryRQ : rq) {
            ProductCategory productCategory = ProductCategory.builder()
                    .categoryName(productCategoryRQ.getCategoryName())
                    .units(productCategoryRQ.getUnits())
                    .state(productCategoryRQ.getState())
                    .build();
            productCategories.add(productCategory);
        }
        return productCategories;
    }

    private List<ProductCategoryRS> transformCategoriesRS(List<ProductCategory> productCategories) {
        List<ProductCategoryRS> productCategoryRS = new ArrayList<>();
        if (productCategories == null) {
            productCategoryRS = null;
        } else {
            for (ProductCategory productCategory : productCategories) {
                ProductCategoryRS rs = ProductCategoryRS.builder()
                        .categoryName(productCategory.getCategoryName())
                        .units(productCategory.getUnits())
                        .state(productCategory.getState())
                        .build();
                productCategoryRS.add(rs);
            }
        }
        return productCategoryRS;
    }
    private ProductCategory transformUpdateCategoryRQ(ProductCategoryRQ rq){
        ProductCategory productCategory = ProductCategory.builder().state(rq.getState()).build();
        return productCategory;
    }

    @Transactional
    public Product productCreate(ProductRQ productRQ){
        Product product = this.transformProductRQ(productRQ);
        Product productTmp = this.productRepository.findFirstByUniqueKey(product.getUniqueKey());
        if (productTmp == null){
            product.setUniqueKey(UUID.randomUUID().toString());
            product.setState("ACT");
            product.setCreationDate(new Date());
            product.setLastModifiedDate(new Date());

            return this.productRepository.save(product);
        } else {
            throw new RuntimeException("Ya existe el producto con id" + product.getId());
        }
    }

    @Transactional
    public Product updateProduct(ProductRQ productRQ, String uuid){
        Product product = this.transformProductRQ(productRQ);
        Product productTmp = this.productRepository.findFirstByUniqueKey(uuid);
        if (productTmp == null) {
            throw new RuntimeException("El producto con uuid " + uuid + " no existe");
        } else {
            productTmp.setName(product.getName());
            productTmp.setLabel(product.getLabel());
            productTmp.setType(product.getType());
            productTmp.setState(product.getState());
            productTmp.setLastModifiedDate(new Date());
            productTmp.setComments(product.getComments());

            return this.productRepository.save(productTmp);
        }
    }

    @Transactional
    public Product deleteProduct(String uuid){
        Product productTmp = this.productRepository.findFirstByUniqueKey(uuid);
        if(productTmp == null){
            throw new RuntimeException("El producto a eliminar no existe");
        } else {
            productTmp.setState("INA");
            productTmp.setLastModifiedDate(new Date());
            return this.productRepository.save(productTmp);
        }
    }

    public ProductRS obtainProductByUniqueKey(String uuid){
        Product product = this.productRepository.findFirstByUniqueKey(uuid);
        ProductRS productTmp = this.transformProductRS(product);
        if (productTmp == null){
            throw new RuntimeException("Parametros de búsqueda incorrectos, mal escrito o no se encuentra con uuid "+uuid);
        } else {
            if ("INA".equals(productTmp.getState())){
                throw new RuntimeException("El producto no está disponible");
            }
            return productTmp;
        }
    }
}
