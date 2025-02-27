package com.creative.ekart.service;

import com.creative.ekart.exception.ApiException;
import com.creative.ekart.exception.ResourceNotFoundException;
import com.creative.ekart.model.Category;
import com.creative.ekart.model.Product;
import com.creative.ekart.payload.ProductDTO;
import com.creative.ekart.payload.ProductResponse;
import com.creative.ekart.repository.CategoryRepository;
import com.creative.ekart.repository.ProductRepository;
import com.creative.ekart.service.interfaces.FileService;
import com.creative.ekart.service.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    @Value("${image.path}")
    private String path;


    public ProductServiceImpl(ProductRepository productRepository,
                              ModelMapper modelMapper,
                              CategoryRepository categoryRepository,
                              FileService fileService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    private ProductResponse getProductResponse(Page<Product> pageProducts) {
        List<ProductDTO> productDtos = pageProducts.getContent().stream()
                .map((product)->modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setTotalElements(pageProducts.getNumberOfElements());
        productResponse.setLast(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String orderBy,String orderDir){
        Sort sortByandOrder = orderDir.equalsIgnoreCase("asc") ?
                Sort.by(orderBy).ascending():
                Sort.by(orderBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Product> products = productRepository.findAll(pageDetails);

        if(!products.hasContent()){
            throw new ApiException("No products found");
        }

        return getProductResponse(products);
    }

    @Override
    public ProductResponse getAllProductsByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String orderBy,String orderDir){

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        Sort sortByandOrder = orderDir.equalsIgnoreCase("asc") ?
                Sort.by(orderBy).ascending():
                Sort.by(orderBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Product> pageProducts = productRepository.findAllByCategory(category,pageDetails);
        if(!pageProducts.hasContent()){
            throw new ApiException("No products found with categoryId : "+categoryId);
        }

        return getProductResponse(pageProducts);
    }

    public ProductResponse getAllProductsByKeyword(String keyword,Integer pageNumber, Integer pageSize, String orderBy,String orderDir){

        Sort sortByandOrder = orderDir.equalsIgnoreCase("asc") ?
                Sort.by(orderBy).ascending():
                Sort.by(orderBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Product> productsPage = productRepository.findAllByProductNameContainingIgnoreCase(keyword,pageDetails);
        if(!productsPage.hasContent()){
            throw new ApiException("No match found with keyword : "+keyword);
        }
        return getProductResponse(productsPage);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO,Long categoryId) {

        Category category = categoryRepository.
                findById(categoryId).
                orElseThrow(()->
                        new ResourceNotFoundException("Category ","categoryId",categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductById(Long id, ProductDTO productDTO) {
        Product retrivedProduct = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product ","id",id));
        Product recievedProduct = modelMapper.map(productDTO, Product.class);


        retrivedProduct.setDescription(recievedProduct.getDescription());
        retrivedProduct.setPrice(recievedProduct.getPrice());
        retrivedProduct.setCategory(recievedProduct.getCategory());
        retrivedProduct.setProductName(recievedProduct.getProductName());
        retrivedProduct.setDiscount(recievedProduct.getDiscount());
        retrivedProduct.setQuantity(recievedProduct.getQuantity());

        Product updatedProduct = productRepository.save(retrivedProduct);
        ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);

        return updatedProductDTO;
    }

    public ProductDTO deleteProductById(Long id){

        Product existingProduct =productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product ","id",id));

        productRepository.delete(existingProduct);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product ","id",productId));

        String fileName = fileService.uploadImage(image,path);
        existingProduct.setImage(fileName);
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }



}
