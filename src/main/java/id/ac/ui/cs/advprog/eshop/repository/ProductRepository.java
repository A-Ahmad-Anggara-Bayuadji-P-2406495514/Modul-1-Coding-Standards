package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    private int idCounter = 1;
    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(String.valueOf(idCounter++));
        }

        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product update(Product product) {
        Product updatedProduct = findById(product.getProductId());
        if (updatedProduct != null) {
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setProductQuantity(product.getProductQuantity());
            return updatedProduct;
        }
        return null;
    }
}
