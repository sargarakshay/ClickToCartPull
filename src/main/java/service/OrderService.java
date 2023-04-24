package service;

import model.Order;

public interface OrderService {
    void placeOrder(final Integer productId, final Integer customerId);

    void viewOrderDetails(final Integer id, final Integer customerId);

    Order getOrderById(final Integer id, final Integer customerId);

}
