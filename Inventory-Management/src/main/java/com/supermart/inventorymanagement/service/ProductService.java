package com.supermart.inventorymanagement.service;

import com.supermart.inventorymanagement.dto.CreateProductRequest;
import com.supermart.inventorymanagement.dto.ProductResponse;
import com.supermart.inventorymanagement.dto.UpdateProductRequest;
import com.supermart.inventorymanagement.model.Inventory;
import com.supermart.inventorymanagement.model.Product;
import com.supermart.inventorymanagement.repository.InventoryRepository;
import com.supermart.inventorymanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final InventoryRepository inventoryRepository;
    private final CallOrderAPIService callOrderAPIService;
    private final ProductMapperService productMapperService;




    public List<ProductResponse> getProductByName(String name){
        List<Product> products =  repository.findByName(name);
        return products.stream().map(productMapperService::mapToProductResponse).toList();
    }

    public List<ProductResponse> getProductsByBrand(String brand){
        List<Product> products = repository.findAllByBrand(brand);
        return products.stream().map(productMapperService::mapToProductResponse).toList();
    }

    public List<ProductResponse> getProductsByCategory(String category){
        List<Product> products = repository.findAllByCategory(category);
        return products.stream().map(productMapperService::mapToProductResponse).toList();
    }

    public List<ProductResponse> getProductsByPriceRange(double minPrice, double maxPrice){
        List<Product> products = repository.findByPriceRange(minPrice,maxPrice);
        return products.stream().map(productMapperService::mapToProductResponse).toList();
    }

    public List<ProductResponse> getProductsByCategoryAndPriceRange(String category, double minPrice, double maxPrice){
        List<Product> products = repository.findAllByCategoryAndPriceRange(category,minPrice,maxPrice);
        return products.stream().map(productMapperService::mapToProductResponse).toList();
    }

    public ProductResponse getProductBySkuCode(String skuCode){
        Inventory inventory=inventoryRepository.findInventoryBySkuCode(skuCode).get();
        String productId=inventory.getProductId();
        return getProductById(productId);
    }


    public Product updateProduct(UpdateProductRequest updateProductRequest) {
        Product existingProduct = repository.findById(updateProductRequest.getId()).orElse(null);
        if (existingProduct != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            Product updatedProduct = Product.builder()
                    .id(existingProduct.getId())
                    .name(updateProductRequest.getName() != null ? updateProductRequest.getName() : existingProduct.getName())
                    .price(updateProductRequest.getPrice() != null
                            ? Double.parseDouble(decimalFormat.format(updateProductRequest.getPrice()))
                            : existingProduct.getPrice())
                    .brand(updateProductRequest.getBrand() != null ? updateProductRequest.getBrand() : existingProduct.getBrand())
                    .category(updateProductRequest.getCategory() != null ? updateProductRequest.getCategory() : existingProduct.getCategory())
                    .inventory(existingProduct.getInventory())
                    .build();
            return repository.save(updatedProduct);
        }
        return null;
    }



}
