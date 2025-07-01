package com.pxc.store_api.carts;

public class CartEmptyExceptions extends RuntimeException{
    public CartEmptyExceptions() {
        super("Cart is empty");
    }
}
