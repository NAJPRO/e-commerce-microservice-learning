package com.orders.orderservice.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.orders.orderservice.client.InventoryClient;
import com.orders.orderservice.client.ProductClient;
import com.orders.orderservice.dto.mapper.OrderMapper;
import com.orders.orderservice.dto.request.OrderItemRequest;
import com.orders.orderservice.dto.request.OrderRequest;
import com.orders.orderservice.dto.request.ReserveStockRequest;
import com.orders.orderservice.dto.response.OrderItemResponse;
import com.orders.orderservice.dto.response.OrderResponse;
import com.orders.orderservice.dto.response.ProductDto;
import com.orders.orderservice.entity.Order;
import com.orders.orderservice.entity.OrderItem;
import com.orders.orderservice.exception.NoStockException;
import com.orders.orderservice.exception.ProductNotFoundException;
import com.orders.orderservice.repository.OrderItemRepository;
import com.orders.orderservice.repository.OrderRepository;
import com.orders.orderservice.service.OrderService;

import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setStatus("PENDING");
        order = orderRepository.save(order);
        log.info("Order created with ID: {}", order.getId());
        log.info("Checking stock for order: {}", order.getId());
        log.info("Size of items in order: {}", orderRequest.getItems().size());
        for (OrderItemRequest item : orderRequest.getItems()) {
            log.info("Checking stock for productId: {}, quantity: {}", item.getProductId(), item.getQuantity());
            ProductDto productDto = productClient.getProductById(item.getProductId()).data();
            log.info("Retrieved ProductDto: {}", productDto);

            if (productDto == null) {
                order.setStatus("REJECTED_PRODUCT_NOT_FOUND");
                orderRepository.save(order);
                throw new ProductNotFoundException("Product with ID " + item.getProductId() + " not found.");
            }
            log.info("ProductDto: {}", productDto.toString());
            Boolean isInStock = false;
            log.info("isInStock before calling inventoryClient: {}", isInStock);
            try {
                log.info("Calling inventoryClient.checkStock for productId: {}, quantity: {}", item.getProductId(), item.getQuantity());
                com.orders.orderservice.payload.ApiResponse<Boolean> apiResponse = inventoryClient.checkStock(item.getProductId(), item.getQuantity());
                log.info("Received ApiResponse from inventoryClient: {}", apiResponse);
                isInStock = apiResponse.data();
            } catch (NoStockException e) {
                log.error("No stock for product: {}", item.getProductId(), e);
                order.setStatus("REJECTED_NO_STOCK");
                orderRepository.save(order);
                throw e;
            }

            log.info("IsInStock: {}", isInStock);
            if (Boolean.FALSE.equals(isInStock)) {
                order.setStatus("REJECTED_NO_STOCK");
                orderRepository.save(order);
                throw new NoStockException(
                        "No stock for product : " + item.getProductId() + ", Name : " + productDto.getName());
            }
        }
        // If it is okay, reserve product
        // Reserve
        log.info("Reserving products for order: {}", order.getId());
        for (OrderItemRequest orderItem : orderRequest.getItems()) {
            ReserveStockRequest body = new ReserveStockRequest();
            body.setQuantity(orderItem.getQuantity());
            inventoryClient.reserveProduct(orderItem.getProductId(), body);
        }
        log.info("Products reserved for order: {}", order.getId());
        // Build Order Items
        for (OrderItemRequest orderItem : orderRequest.getItems()) {
            OrderItem item = new OrderItem();
            ProductDto productDto = productClient.getProductById(item.getProductId()).data();
            item.setOrder(order);
            item.setProductId(productDto.getId());
            item.setProductName(productDto.getName());
            item.setUnitPrice(productDto.getPrice());
            item.setQuantity(orderItem.getQuantity());

            item.setSubTotal(productDto.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            orderItemRepository.save(item);
            // Confirm
            ReserveStockRequest body = new ReserveStockRequest();
            body.setQuantity(orderItem.getQuantity());
            inventoryClient.confirmProductReservation(item.getProductId(), body);
        }
        log.info("Order created with ID: {}", order.getId());
        order.setTotalAmount(calculateTotal(order.getId()));
        order.setStatus("CONFIRMED");
        log.info("Order confirmed with ID: {}", order.getId());
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public Page<OrderResponse> getAllOrders(int page, int size) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page, size));
        return orders.map(o -> orderMapper.toResponse(o));
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with ID " + orderId + " don't exist."));
        return orderMapper.toResponse(order);
    }

    @Override
    public Page<OrderResponse> getOrderByStatus(String status, int page, int size) {
        Page<Order> orders = orderRepository.findAllByStatus(status, PageRequest.of(page, size));
        return orders.map(o -> orderMapper.toResponse(o));
    }

    @Override
    public Page<OrderResponse> getOrdersByCustomer(String customerName, int page, int size) {
        Page<Order> orders = orderRepository.findAllByCustomerName(customerName, PageRequest.of(page, size));
        return orders.map(o -> orderMapper.toResponse(o));
    }

    @Override
    public BigDecimal calculateTotal(String orderId) {
        List<OrderItemResponse> orderItems = getOrderItems(orderId);
        BigDecimal amount = new BigDecimal(0);
        for (OrderItemResponse orderItemResponse : orderItems) {
            amount = amount.add(orderItemResponse.getSubTotal());
        }
        return amount;
    }

    @Override
    public Void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with ID " + orderId + " don't exist."));
        if (order.getStatus().equals("CONFIRMED")) {
            order.setStatus("CANCELLED");
            orderRepository.save(order);
        }
        return null;
    }

    @Override
    public List<OrderItemResponse> getOrderItems(String orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderMapper.toOrderItemResponse(orderItems);
    }

}
