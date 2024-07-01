package com.ctrl.demo.service;

import com.ctrl.demo.model.Product;
import com.ctrl.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProductStock(Long id, int stock) {
        Product product = getProductById(id);
        if (product != null) {
            product.setStock(stock);
            return productRepository.save(product);
        }
        return null;
    }
}
