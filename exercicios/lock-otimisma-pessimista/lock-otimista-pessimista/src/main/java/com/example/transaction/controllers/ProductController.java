package com.example.transaction.controllers;

import com.example.transaction.models.Product;
import com.example.transaction.models.TransactionDto;
import com.example.transaction.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public Product findById(@PathVariable long id) {
        return productService.findById(id);
    }

    @PostMapping("/product/transact")
    public void withdraw(@RequestBody TransactionDto<Integer> transactionDto) {
        productService.transact(transactionDto);
    }
}
