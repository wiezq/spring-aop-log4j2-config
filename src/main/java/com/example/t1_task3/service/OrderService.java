package com.example.t1_task3.service;

import com.example.t1_task3.model.OrderEntity;
import com.example.t1_task3.model.OrderStatus;
import com.example.t1_task3.model.ProductEntity;
import com.example.t1_task3.model.UserEntity;
import com.example.t1_task3.repository.OrderEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
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

    public OrderEntity getOrderById(Long id) {
        return orderEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + id + " not found"));
    }

    public List<OrderEntity> getAllOrders() {
        return orderEntityRepository.findAll();
    }

    public OrderEntity updateOrder(Long id, String description, OrderStatus status) {
        OrderEntity order = getOrderById(id);
        if (description != null) order.setDescription(description);
        if (status != null) order.setStatus(status);
        return orderEntityRepository.save(order);
    }

    public void deleteOrder(Long id) {
        OrderEntity order = getOrderById(id);
        orderEntityRepository.delete(order);
    }
}
