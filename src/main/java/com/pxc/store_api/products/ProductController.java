package com.pxc.store_api.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name = "categoryId", required = false) Byte categoryId){
        List<Product> products;
        if(categoryId == null){
            products = productRepository.findAllWithCategory();
        }else{
            products = productRepository.findByCategoryId(categoryId);
        }
        return products.stream()
                .map(p -> productMapper.toDto(p)).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);

        if(product == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productMapper.toDto(product)) ;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, UriComponentsBuilder uriBuilder){
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category==null)
            return ResponseEntity.badRequest().build();

        Product product = productMapper.toProductEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);

        productDto.setId(product.getId());

        URI uri = uriBuilder.path("products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category==null)
            return ResponseEntity.badRequest().build();

        Product product = productRepository.findById(id).orElse(null);
        if(product==null)
            return ResponseEntity.notFound().build();

//        productDto.setId(id);
        productMapper.update(productDto, product);

        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(id);

        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        Product product = productRepository.findById(id).orElse(null);
        if(product==null)
            return ResponseEntity.notFound().build();

        productRepository.delete(product);

        return ResponseEntity.noContent().build();
    }

}
