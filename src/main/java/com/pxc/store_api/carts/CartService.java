package com.pxc.store_api.carts;

import com.pxc.store_api.products.ProductNotFoundException;
import com.pxc.store_api.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto createCart(){
        Cart cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
            throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);
        if(product==null)
            throw new ProductNotFoundException();

        CartItem cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
            throw new CartNotFoundException();

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
            throw new CartNotFoundException();

        CartItem cartItem = cart.getItem(productId);
        if(cartItem==null)
            throw new ProductNotFoundException();

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void deleteItem(UUID cartId, Long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
            throw new CartNotFoundException();

        cart.removeItem(productId);

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
            throw new CartNotFoundException();

        cart.clear();

        cartRepository.save(cart);
    }

    public Map<String, UUID> getAllCarts(){
        int count =0;
        Map<String, UUID> allCarts = new HashMap<>();
        List<Cart> cartList = cartRepository.findAll();
        for(var ca: cartList){
            count++;
            allCarts.put("cartId "+ count, ca.getId());
        }

        return allCarts;
    }
}
