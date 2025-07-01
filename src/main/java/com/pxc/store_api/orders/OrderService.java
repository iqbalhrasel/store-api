package com.pxc.store_api.orders;

import com.pxc.store_api.auth.AuthService;
import com.pxc.store_api.users.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        User user = authService.getCurrentUser();

        List<Order> orders = orderRepository.getAllByCustomer(user);

        return orders.stream().map(order -> orderMapper.toDto(order)).toList();
    }

    public OrderDto getOrder(Long orderId) {
        User user = authService.getCurrentUser();

        var order = orderRepository.getOrderWithItems(orderId).orElse(null);
        if(order == null)
            throw new OrderNotFoundException();

        if(!order.isPlacedBy(user))
            throw new AccessDeniedException("You don't have access to this order");

        return orderMapper.toDto(order);
    }
}
