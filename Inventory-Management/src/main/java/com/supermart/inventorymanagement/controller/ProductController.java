package com.supermart.inventorymanagement.controller;


import com.supermart.inventorymanagement.dto.CreateProductRequest;
import com.supermart.inventorymanagement.dto.ProductResponse;
import com.supermart.inventorymanagement.dto.UpdateProductRequest;
import com.supermart.inventorymanagement.service.ProductService;
import com.supermart.inventorymanagement.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/inventory/product")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductResponse> getProducts(){ return service.getAllProducts(); }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable  String id){
        return service.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public List<ProductResponse> getProductByName(@PathVariable String name){
        return service.getProductByName(name);
    }

    @GetMapping("/brand/{brand}")
    public List<ProductResponse> getProductsByBrand(@PathVariable String brand) {return service.getProductsByBrand(brand);}

    @GetMapping("category/{category}")
    public List<ProductResponse> getAllProductsByCategory(@PathVariable String category){
        return service.getProductsByCategory(category);
    }

    @GetMapping("sku-code/{sku-code}")
    public ProductResponse getProductBySkuCode(@PathVariable("sku-code") String skuCode){
        return service.getProductBySkuCode(skuCode);
    }

    @GetMapping("price/{minPrice}/{maxPrice}")
    public List<ProductResponse> getProductsByPriceRange(
            @PathVariable double minPrice,
            @PathVariable double maxPrice
    ){
        return service.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("category-price/{category}/{minPrice}/{maxPrice}")
    public List<ProductResponse> getProductsByCategoryAndPriceRange(
            @PathVariable String category,
            @PathVariable double minPrice,
            @PathVariable double maxPrice
    ){
        return service.getProductsByCategoryAndPriceRange(category, minPrice, maxPrice);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody CreateProductRequest createProductRequest){
        return service.addProduct(createProductRequest);
    }

    @PatchMapping
    public String updateProduct(@RequestBody UpdateProductRequest updateProductRequest){
         service.updateProduct(updateProductRequest);
         return "Product with id :" + updateProductRequest.getId() + " updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id){
        return service.deleteProduct(id);
    }

}
