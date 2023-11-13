package com.backend.BackendJWT.Controllers;

import com.backend.BackendJWT.Models.Product.ProductEntity;
import com.backend.BackendJWT.Models.Product.ProductRequestDto;
import com.backend.BackendJWT.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@Valid ProductRequestDto createProductDTO,
                                         @RequestParam("file") MultipartFile imagen) throws IOException {

        ProductEntity savedProduct = productService.saveProduct(createProductDTO, imagen);

        return ResponseEntity.ok(savedProduct);
    }
}
