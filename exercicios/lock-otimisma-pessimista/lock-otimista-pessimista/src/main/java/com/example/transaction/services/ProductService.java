package com.example.transaction.services;


import com.example.transaction.models.Product;
import com.example.transaction.models.TransactionDto;
import com.example.transaction.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Transactional()
    public void transact(TransactionDto<Integer> transactionDto) {
        Product product = findById(transactionDto.id());
        int newStock = product.getStock() + transactionDto.value();
        if (newStock < 0) throw new RuntimeException("Insufficient stock");
        product.setStock(newStock);
    }
}
