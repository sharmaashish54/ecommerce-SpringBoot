package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if(isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);

            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * .01) * product.getPrice());
            product.setSpecialPrice(specialPrice);

            Product savedProduct = productRepository.save(product);

            return modelMapper.map(savedProduct, ProductDTO.class);
        }else{
            throw new APIException("Product already Exists !!");
        }
    }

    @Override
    public ProductResponse getAllProducts() {

        List<Product> productList = productRepository.findAll();
        if(productList.isEmpty()){
            throw  new APIException("No Products Present!!");
        }
        List<ProductDTO> productDTOS = productList.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)
        ).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = productRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId)).getCategory();

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        if(products.isEmpty()){
            throw  new APIException("No Products Present for this category id!!");
        }
        List<ProductDTO> productDTOS = products.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)
        ).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {

        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        if(products.isEmpty()){
            throw  new APIException("No Products Present for this keyword!!");
        }
        List<ProductDTO> productDTOS = products.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)
        ).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
            Product product = modelMapper.map(productDTO,Product.class);

            productFromDb.setProductName(product.getProductName());
            productFromDb.setDescription(product.getDescription());
            productFromDb.setQuantity(product.getQuantity());
            productFromDb.setDiscount(product.getDiscount());
            productFromDb.setPrice(product.getPrice());

            double specialPrice = product.getPrice() - ((product.getDiscount() * .01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            productFromDb.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(productFromDb);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get the product from DB
        Product product =  productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productid", productId));

        //Upload File to server
        //Get the file name of uploaded image
        String filename= fileService.uploadImage(path, image);
        product.setImage(filename);
        //save product
        Product updatedProduct = productRepository.save(product);
        //Return DTO
        return modelMapper.map(updatedProduct,ProductDTO.class);
    }


}

