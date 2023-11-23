package kg.neobis.neo_land.controller;

import kg.neobis.neo_land.dto.ProductDto;
import kg.neobis.neo_land.service.ProductService;
import io.swagger.annotations.Api;
import kg.neobis.neo_land.config.SwaggerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = SwaggerConfig.PRODUCT)
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity saveProduct(@RequestBody ProductDto productDto){
        try {
            return ResponseEntity.ok(productService.addProduct(productDto));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }

    }

    @PutMapping("/update/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id){
        return productService.updateProduct(productDto, id);
    }

    @GetMapping("/findAll")
    public List<ProductDto> findAll(){
        return productService.findAllProducts();
    }

    @GetMapping("/findById")
    public ProductDto findById(@RequestParam Long id){
        return productService.getProductById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
