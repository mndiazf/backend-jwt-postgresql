package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Models.Images.ImageEntity;
import com.backend.BackendJWT.Models.Product.ProductEntity;
import com.backend.BackendJWT.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ImageEntity saveImage(MultipartFile image, Long productId) throws IOException {
        // Verificar si la imagen no está vacía
        if (image != null && !image.isEmpty()) {
            // Obtener el nombre final de la imagen
            String nombreFinalImagen = generateImageName(image.getOriginalFilename());

            // Crear la ImageEntity y asignarle el producto
            ImageEntity imageEntity = ImageEntity.builder()
                    .base64Data(convertFileToBase64(image))
                    .product(ProductEntity.builder().id(productId).build())
                    .build();

            // Guardar la imagen en la base de datos
            return imageRepository.save(imageEntity);
        } else {
            throw new IllegalArgumentException("La imagen no puede estar vacía.");
        }
    }

    private String convertFileToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    private String generateImageName(String originalFilename) {
        // Generar un nombre único para la imagen (puedes ajustar según tus necesidades)
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }
}
