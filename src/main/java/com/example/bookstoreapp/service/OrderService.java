package com.example.bookstoreapp.service;
import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.exceptions.BookStoreCustomException;
import com.example.bookstoreapp.model.CartData;
import com.example.bookstoreapp.model.OrderData;
import com.example.bookstoreapp.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ICartService iCartService;

    public OrderData addOrderDetails(OrderDTO orderDTO) {
        CartData cartData = iCartService.getCartById(orderDTO.getCartId());
        OrderData orderData = new OrderData(cartData, orderDTO);
        return orderRepository.save(orderData);
    }

    @Override
    public OrderData getOrderDetailsByOrderId(Long orderId) {
        Optional<OrderData> orderDetails = orderRepository.findById(orderId);
        if(orderDetails.isPresent()){
            return orderDetails.get();
        }else
            throw new BookStoreCustomException("Order ID does not exist");
    }
    @Override
    public void deleteOrderByOrderId(Long orderId) {
            OrderData orderData = this.getOrderDetailsByOrderId(orderId);
            orderData.setCancel(true);
            orderRepository.save(orderData);
    }
    @Override
    public List<OrderData> getAllOrders() {
        return orderRepository.findAll();
    }

}
