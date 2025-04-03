package com.creative.ekart.service;

import com.creative.ekart.exception.ApiException;
import com.creative.ekart.exception.ResourceNotFoundException;
import com.creative.ekart.model.Cart;
import com.creative.ekart.model.CartItem;
import com.creative.ekart.model.Product;
import com.creative.ekart.model.User;
import com.creative.ekart.payload.CartDTO;
import com.creative.ekart.payload.CartItemDTO;
import com.creative.ekart.payload.ProductDTO;
import com.creative.ekart.repository.CartItemRepository;
import com.creative.ekart.repository.CartRepository;
import com.creative.ekart.repository.ProductRepository;
import com.creative.ekart.repository.UserRepository;
import com.creative.ekart.service.interfaces.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ModelMapper modelMapper, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }
    @Override
    public CartDTO getCartbyUser(UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new ApiException("user not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->
                        new ResourceNotFoundException("Cart","user",user.getUsername()));
        CartDTO cartDTO = cartToCartDTO(cart);

        return cartDTO;
    }

    public CartDTO addProductToCart(UserDetails userDetails,Long productId, int quantity) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("user","user ",userDetails.getUsername()));
        Cart cart = findOrCreateCart(user);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getCartId(),productId);
        if(item != null) {
            throw new ApiException("Product Already Exists in the cart");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product","product Id ",productId));
        if(product.getQuantity() < quantity) {
            throw new ApiException("the available quantity "+product.getQuantity()+" is less than the specified quantity "+quantity);
        }



        CartItem cartItem = productToCartItem(product,quantity);
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);

        cart = cartRepository.save(cart);

        CartDTO cartDTO = cartToCartDTO(cart);

        return cartDTO;
    }

    @Override
    public CartItemDTO updateQuantity(UserDetails userDetails, Long productId, Integer quantity) {
        if(quantity<0){
            throw new ApiException("quantity is less than zero");
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("user","user ",userDetails.getUsername()));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("cart","user ",user.getUsername()));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(),productId);
        if(cartItem == null){
            throw new ResourceNotFoundException("cartItem","cartItem ",productId);
        }
        if(quantity==0){
            cart.getCartItems().remove(cartItem);
            cartRepository.save(cart);
            return new CartItemDTO();
        }
        if(cartItem.getProduct().getQuantity() < quantity ) {
            throw new ApiException("the available quantity "+cartItem.getProduct().getQuantity()
                    + " is less than the specified quantity "+(quantity));
        }
        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);
        CartItemDTO cartItemDTO = cartItemToCartItemDto(cartItem);
        return cartItemDTO;
    }

    @Override
    public boolean removeProduct(Long productId, UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("user","user ",userDetails.getUsername()));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("cart","user ",user.getUsername()));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(),productId);
        if(cartItem == null){
            return false;
        }
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
        return true;
    }

    private CartItemDTO cartItemToCartItemDto(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getCartItemId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
        cartItemDTO.setProduct(productDTO);
        return cartItemDTO;
    }

    private Cart findOrCreateCart(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElse(null);
        if(cart == null){
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    private CartDTO cartToCartDTO(Cart cart) {
        List<ProductDTO> productDTOS = cartItemToProductDTO(cart.getCartItems());
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setProducts(productDTOS);
        cartDTO.setTotalPrice(cart.getTotalPrice());
        return cartDTO;
    }

    private List<ProductDTO> cartItemToProductDTO(List<CartItem> cartItems) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setQuantity(cartItem.getQuantity());
            productDTO.setProductName(cartItem.getProduct().getProductName());
            productDTO.setProductDescription(cartItem.getProduct().getDescription());
            productDTO.setProductId(cartItem.getProduct().getProductId());
            productDTO.setPrice(cartItem.getProduct().getPrice());
            productDTO.setDiscount(cartItem.getDiscount());
            productDTOS.add(productDTO);
        });

        return productDTOS;
    }

    private CartItem productToCartItem(Product product,int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
      
        cartItem.setDiscount(product.getDiscount());
        return cartItem;

    }
}
