package com.pxc.store_api.payments;

import com.pxc.store_api.carts.CartEmptyExceptions;
import com.pxc.store_api.carts.CartNotFoundException;
import com.pxc.store_api.carts.Cart;
import com.pxc.store_api.orders.Order;
import com.pxc.store_api.carts.CartRepository;
import com.pxc.store_api.orders.OrderRepository;
import com.pxc.store_api.auth.AuthService;
import com.pxc.store_api.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    public CheckoutResponse checkout(CheckoutRequest request) {
        Cart cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();

        if(cart.isEmpty())
            throw new CartEmptyExceptions();

        Order order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        try{
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException pe){
            orderRepository.delete(order);
            throw pe;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway.parseWebhookRequest(request)
            .ifPresent(paymentResult -> {
                Order order = orderRepository.findById( paymentResult.getOrderId() ).orElseThrow();
                order.setStatus(paymentResult.getStatus());
                orderRepository.save(order);
            });
    }
}
