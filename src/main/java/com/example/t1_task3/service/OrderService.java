package com.example.t1_task3.service;

import com.example.t1_task3.aspect.Loggable;
import com.example.t1_task3.model.OrderEntity;
import com.example.t1_task3.model.OrderStatus;
import com.example.t1_task3.model.ProductEntity;
import com.example.t1_task3.model.UserEntity;
import com.example.t1_task3.repository.OrderEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Loggable
public class OrderService {
    private final OrderEntityRepository orderEntityRepository;
    private final ProductService productService;
    private final UserService userService;

    public OrderEntity createOrder(Long userId, String description, List<Long> productIds) {
        UserEntity user = userService.getUserById(userId);
        OrderEntity order = new OrderEntity();
        order.setDescription(description);
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);

        List<ProductEntity> products = productService.getProductsByIds(productIds);
        order.setProducts(products);

        return orderEntityRepository.save(order);
    }

    public OrderEntity getOrderById(Long orderId) {
        return orderEntityRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found"));
    }

    public List<OrderEntity> getAllOrders() {
        return orderEntityRepository.findAll();
    }

    public OrderEntity updateOrder(Long orderId, String description, OrderStatus status) {
        OrderEntity order = getOrderById(orderId);
        if (description != null) order.setDescription(description);
        if (status != null) order.setStatus(status);
        return orderEntityRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        OrderEntity order = getOrderById(orderId);
        orderEntityRepository.delete(order);
    }
}
