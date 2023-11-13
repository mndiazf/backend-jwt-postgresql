package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Models.Product.CategoryEntity;
import com.backend.BackendJWT.Models.Product.ECategory;
import com.backend.BackendJWT.Models.Product.ProductEntity;
import com.backend.BackendJWT.Models.Product.ProductRequestDto;
import com.backend.BackendJWT.Repositories.CategoryRepository;
import com.backend.BackendJWT.Repositories.ProductRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;



    public ProductEntity saveProduct(ProductRequestDto productRequestDto) throws IOException {

        Set<CategoryEntity> categories = convertCategoryNamesToEntities(productRequestDto.getCategory());


        // Crear el objeto ProductEntity
        ProductEntity productEntity = ProductEntity.builder()
                .nombre(productRequestDto.getNombre())
                .descripcion(productRequestDto.getDescripcion())
                .marca(productRequestDto.getMarca())
                .precio(productRequestDto.getPrecio())
                .stock(productRequestDto.getStock())
                .oferta(productRequestDto.isOferta())
                .category(categories)
                .build();

        return productRepository.save(productEntity);
    }

    private String handleImage(MultipartFile imagen) throws IOException {
        if (!imagen.isEmpty()) {
            byte[] bytesImg = imagen.getBytes();
            return Base64.encodeBase64String(bytesImg);
        }
        return null; // O manejar según tus requerimientos específicos si no hay imagen
    }

    private Set<CategoryEntity> convertCategoryNamesToEntities(Set<String> categoryNames) {
        return categoryNames.stream()
                .map(this::getOrCreateCategoryEntity)
                .collect(Collectors.toSet());
    }

    private CategoryEntity getOrCreateCategoryEntity(String categoryName) {
        ECategory categoryEnum = ECategory.valueOf(categoryName.toUpperCase());

        Optional<CategoryEntity> existingCategory = Optional.ofNullable(categoryRepository.findByName(categoryEnum));

        return existingCategory.orElseGet(() -> saveNewCategory(categoryEnum));
    }

    private CategoryEntity saveNewCategory(ECategory categoryEnum) {
        return categoryRepository.save(CategoryEntity.builder().name(categoryEnum).build());
    }

}
