package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.model.OrderData;

import java.util.List;

public interface IOrderService {
    OrderData addOrderDetails(OrderDTO orderDTO);

    OrderData getOrderDetailsByOrderId(Long orderId);
    void deleteOrderByOrderId(Long orderId);
    List<OrderData> getAllOrders();
}
