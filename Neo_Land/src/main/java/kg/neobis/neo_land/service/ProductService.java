package kg.neobis.neo_land.service;

import kg.neobis.neo_land.dto.ProductDto;
import kg.neobis.neo_land.entity.Product;
import kg.neobis.neo_land.exception.RecordNotFoundException;
import kg.neobis.neo_land.mapper.ProductMapper;
import kg.neobis.neo_land.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto addProduct(ProductDto productDto){
        Product product = productMapper.toEntity(productDto);
        Product productSave = productRepository.save(product);
        return productMapper.toModel(productSave);
    }

    public ProductDto updateProduct(ProductDto productDto, Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Продукт не найден"));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return productMapper.toModel(product);
    }

    public List<ProductDto> findAllProducts(){
        return productMapper.toModelList(productRepository.findAll());
    }

    public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Продукт не найден"));
        return productMapper.toModel(product);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Продукт не найден"));
        productRepository.deleteById(product.getId());
    }
}
