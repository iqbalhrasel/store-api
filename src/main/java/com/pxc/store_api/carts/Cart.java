package com.pxc.store_api.carts;

import com.pxc.store_api.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal calculateTotalPrice(){
        BigDecimal total = BigDecimal.ZERO;
        for(var item : items){
            total = total.add(item.calculateTotalPrice());
        }

        return total;
    }

    public CartItem getItem(Long productId){
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product){
        var cartItem = getItem(product.getId());

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(this);

            items.add(cartItem);
        }

        return cartItem;
    }

    public void removeItem(Long productId){
        CartItem cartItem = getItem(productId);
        if(cartItem!=null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clear(){
        items.clear();
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
