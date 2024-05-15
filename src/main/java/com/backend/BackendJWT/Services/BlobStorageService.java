package com.backend.BackendJWT.Services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BlobStorageService {

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(fileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        String blobUrl = blobClient.getBlobUrl();
        return blobUrl.toString();
    }
    public boolean deleteFile(String fileName) {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(fileName);

            blobClient.deleteIfExists();
            return true;
        } catch (BlobStorageException e) {
            if (e.getErrorCode() == BlobErrorCode.BLOB_NOT_FOUND) {
                // Log o manejo si el archivo no existe
                System.out.println("El archivo no existe o ya fue eliminado.");
            } else {
                // Log o manejo de otros errores del almacenamiento de blob
                System.out.println("Error al eliminar el archivo: " + e.getMessage());
            }
            return false;
        }
    }
}