package com.backend.BackendJWT.Controllers.Shop;


import com.backend.BackendJWT.Models.Shop.Categoria;
import com.backend.BackendJWT.Models.Shop.Marca;
import com.backend.BackendJWT.Models.Shop.Producto;
import com.backend.BackendJWT.Services.BlobStorageService;
import com.backend.BackendJWT.Services.Shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/shop/productos")
public class ShopController {

    @Autowired
    private ShopService productoService;

    @Autowired
    private BlobStorageService blobStorageService;


    // Crear un nuevo producto
    @PostMapping("/save")
    public ResponseEntity<Producto> guardarProducto(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("nombre") String nombre,
                                                    @RequestParam("precio") Double precio,
                                                    @RequestParam("stock") Integer stock,
                                                    @RequestParam("descripcion") String descripcion,
                                                    @RequestParam("imgUrl") String imgUrl,
                                                    @RequestParam("descuento") Boolean descuento,
                                                    @RequestParam("porcentajeDescuento") Double porcentajeDescuento,
                                                    @RequestParam("categoriaNombre") String categoriaNombre,
                                                    @RequestParam("marcaNombre") String marcaNombre) {
        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setDescripcion(descripcion);
            producto.setImgUrl(imgUrl);
            producto.setDescuento(descuento);
            producto.setPorcentajeDescuento(porcentajeDescuento);

            Categoria categoria = new Categoria();
            categoria.setNombre(categoriaNombre);
            producto.setCategoria(categoria);

            Marca marca = new Marca();
            marca.setNombre(marcaNombre);
            producto.setMarca(marca);

            String imageUrl = blobStorageService.uploadFile(file, file.getOriginalFilename());
            producto.setImgUrl(imageUrl);

            Producto productoGuardado = productoService.guardarProducto(producto);
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Long id,
                                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                                   @RequestParam("nombre") String nombre,
                                                   @RequestParam("precio") Double precio,
                                                   @RequestParam("stock") Integer stock,
                                                   @RequestParam("descripcion") String descripcion,
                                                   @RequestParam("descuento") Boolean descuento,
                                                   @RequestParam("porcentajeDescuento") Double porcentajeDescuento,
                                                   @RequestParam("categoriaNombre") String categoriaNombre,
                                                   @RequestParam("marcaNombre") String marcaNombre) {
        try {
            Producto productoActualizado = new Producto();
            productoActualizado.setNombre(nombre);
            productoActualizado.setPrecio(precio);
            productoActualizado.setStock(stock);
            productoActualizado.setDescripcion(descripcion);
            productoActualizado.setDescuento(descuento);
            productoActualizado.setPorcentajeDescuento(porcentajeDescuento);

            Categoria categoria = new Categoria();
            categoria.setNombre(categoriaNombre);
            productoActualizado.setCategoria(categoria);

            Marca marca = new Marca();
            marca.setNombre(marcaNombre);
            productoActualizado.setMarca(marca);

            Producto productoEditado = productoService.editarProducto(id, productoActualizado, file);
            return new ResponseEntity<>(productoEditado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/all")
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        try {
            List<Producto> productos = productoService.obtenerTodosLosProductos();
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        try {
            Producto producto = productoService.obtenerProductoPorId(id);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> borrarProductoPorId(@PathVariable Long id) {
        try {
            productoService.borrarProductoPorId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractFileName(String blobUrl) {
        // Implementa lógica para extraer el nombre del archivo del URL, si es necesario
        return blobUrl.substring(blobUrl.lastIndexOf('/') + 1);
    }


    @GetMapping("/allmarcas")
    public ResponseEntity<List<Marca>> obtenerTodasLasMarcas() {
        try {
            List<Marca> marcas = productoService.obtenerTodasLasMarcas();
            if (marcas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allcategorias")
    public ResponseEntity<List<Categoria>> obtenerTodasLasCategorias() {
        try {
            List<Categoria> categorias = productoService.obtenerTodasLasCategorias();
            if (categorias.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categorias, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}





