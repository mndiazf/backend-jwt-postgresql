package com.backend.BackendJWT.Services.Shop;

import com.backend.BackendJWT.Models.Shop.Categoria;
import com.backend.BackendJWT.Models.Shop.Marca;
import com.backend.BackendJWT.Models.Shop.Producto;
import com.backend.BackendJWT.Repositories.Shop.CategoriaRepository;
import com.backend.BackendJWT.Repositories.Shop.MarcaRepository;
import com.backend.BackendJWT.Repositories.Shop.ProductoRepository;
import com.backend.BackendJWT.Services.BlobStorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private BlobStorageService blobStorageService;

    @Transactional
    public Producto guardarProducto(Producto producto) {
        // Convertir el nombre de la marca y categoría a minúsculas con la primera letra en mayúsculas
        Marca marca = convertirStringToMarca(producto.getMarca().getNombre());
        Categoria categoria = convertirStringToCategoria(producto.getCategoria().getNombre());

        // Buscar la marca y categoría por nombre en la base de datos
        Optional<Marca> marcaBD = marcaRepository.findByNombre(marca.getNombre());
        Optional<Categoria> categoriaBD = categoriaRepository.findByNombre(categoria.getNombre());

        // Si la marca o categoría no existe en la base de datos, guardarla
        if (!marcaBD.isPresent()) {
            marca = marcaRepository.save(marca);
        } else {
            marca = marcaBD.get();
        }

        if (!categoriaBD.isPresent()) {
            categoria = categoriaRepository.save(categoria);
        } else {
            categoria = categoriaBD.get();
        }

        // Asignar la marca y categoría al producto
        producto.setMarca(marca);
        producto.setCategoria(categoria);

        // Guardar el producto en la base de datos
        return productoRepository.save(producto);
    }


    @Transactional
    public Producto editarProducto(Long idProducto, Producto productoActualizado, MultipartFile file) throws IOException {
        Optional<Producto> productoBD = productoRepository.findById(idProducto);

        if (productoBD.isPresent()) {
            Producto producto = productoBD.get();

            // Eliminar la imagen anterior si existe
            if (producto.getImgUrl() != null && !producto.getImgUrl().isEmpty()) {
                String fileName = producto.getImgUrl().substring(producto.getImgUrl().lastIndexOf('/') + 1);
                blobStorageService.deleteFile(fileName);
            }

            // Subir la nueva imagen
            String newImageUrl = blobStorageService.uploadFile(file, file.getOriginalFilename());
            producto.setImgUrl(newImageUrl);

            // Actualizar los campos del producto
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setDescuento(productoActualizado.getDescuento());
            producto.setPorcentajeDescuento(productoActualizado.getPorcentajeDescuento());

            // Convertir y establecer la marca y categoría
            Marca marca = convertirStringToMarca(productoActualizado.getMarca().getNombre());
            Categoria categoria = convertirStringToCategoria(productoActualizado.getCategoria().getNombre());

            // Buscar la marca y categoría por nombre en la base de datos
            Optional<Marca> marcaBD = marcaRepository.findByNombre(marca.getNombre());
            Optional<Categoria> categoriaBD = categoriaRepository.findByNombre(categoria.getNombre());

            // Si la marca o categoría no existe en la base de datos, guardarla
            if (!marcaBD.isPresent()) {
                marca = marcaRepository.save(marca);
            } else {
                marca = marcaBD.get();
            }

            if (!categoriaBD.isPresent()) {
                categoria = categoriaRepository.save(categoria);
            } else {
                categoria = categoriaBD.get();
            }

            // Asignar la marca y categoría al producto
            producto.setMarca(marca);
            producto.setCategoria(categoria);

            // Guardar el producto actualizado en la base de datos
            return productoRepository.save(producto);
        } else {
            throw new IllegalArgumentException("No se encontró el producto con el ID: " + idProducto);
        }
    }


    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long idProducto) {
        Optional<Producto> productoBD = productoRepository.findById(idProducto);
        if (productoBD.isPresent()) {
            return productoBD.get();
        } else {
            throw new IllegalArgumentException("No se encontró el producto con el ID: " + idProducto);
        }
    }

    public void borrarProductoPorId(Long idProducto) {
        if (productoRepository.existsById(idProducto)) {
            productoRepository.deleteById(idProducto);
        } else {
            throw new IllegalArgumentException("No se encontró el producto con el ID: " + idProducto);
        }
    }


    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }



    public Marca convertirStringToMarca(String nombre) {
        Marca marca = new Marca();
        marca.setNombre(capitalizar(nombre));
        return marca;
    }


    public Categoria convertirStringToCategoria(String nombre) {
        Categoria categoria = new Categoria();
        categoria.setNombre(capitalizar(nombre));
        return categoria;
    }

    public String capitalizar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }




}