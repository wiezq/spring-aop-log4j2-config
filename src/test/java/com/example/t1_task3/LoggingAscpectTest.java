package com.example.t1_task3;

import com.example.t1_task3.model.OrderEntity;
import com.example.t1_task3.model.OrderStatus;
import com.example.t1_task3.model.ProductEntity;
import com.example.t1_task3.model.UserEntity;
import com.example.t1_task3.service.OrderService;
import com.example.t1_task3.service.ProductService;
import com.example.t1_task3.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LoggingAscpectTest {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Test
    public void testLoggingAspect() {
        // Create for user, products and order

        // User
        UserEntity user = userService.createUser("John", "john@example.com");

        // Product
        ProductEntity product1 = productService.createProduct("Product 1", 100);
        ProductEntity product2 = productService.createProduct("Product 2", 200);
        List<Long> productIds = new ArrayList<>();
        productIds.add(product1.getId());
        productIds.add(product2.getId());

        // Order
        OrderEntity order = orderService.createOrder(user.getId(), "Order 1", productIds);

        // Find user, product and order
        // User
        userService.getUserById(user.getId());
        // Product
        productService.getProductById(product1.getId());
        // Order
        orderService.getOrderById(order.getId());

        // Update user, product and order
        // User
        UserEntity updatedUser = userService.updateUser(user.getId(), "John Doe", "doe@example.com");
        // Product
        ProductEntity updatedProduct1 = productService.updateProduct(product1.getId(), "Product 1 updated", 150);
        // Order
        OrderEntity updatedOrder = orderService.updateOrder(order.getId(), "Order 1 updated", OrderStatus.COMPLETED);

        // Delete
        // User
        userService.deleteUser(updatedUser.getId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.getUserById(updatedUser.getId()));
    }
}
