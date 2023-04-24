package service.impl;

import enums.OrderStatus;
import exception.ClickToCartException;
import model.Customer;
import model.Order;
import model.Product;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class OrderServiceImpl implements OrderService {
    private static final ProductService productService = new ProductServiceImpl();
    private static final CustomerService customerService = new CustomerServiceImpl();
    private static final Scanner sc = new Scanner(System.in);
    private static Integer orderId = 1;

    @Override
    public void placeOrder(final Integer productId, final Integer customerId) {
        final Product product = productService.getProductById(productId);
        final Customer customer = customerService.getCustomerById(customerId);
        final Order order = new Order(orderId++, Collections.singletonList(product), OrderStatus.ORDERED, customer);
        customer.getOrders().add(order);
        customerService.updateCustomer(customer);
        viewOrderDetails(order.getId(), customerId);
    }

    @Override
    public void viewOrderDetails(final Integer id, final Integer customerId) {
        int orderDetailsMenuChoice;
        do {
            final Order order = getOrderById(id, customerId);
            System.out.println();
            System.out.println("+------------------------------+");
            System.out.println("|         Order Details        |");
            System.out.println("+------------------------------+");
            System.out.println("| Order ID: " + order.getId());
            System.out.println("|                              |");
            System.out.println("| Name: " + order.getProducts().get(0).getName());
            System.out.println("| Price: ₹" + order.getProducts().get(0).getPrice());
            System.out.println("| Status: " + order.getStatus());
            System.out.println("+------------------------------+");
            System.out.println("| 1. Goto Dashboard            |");
            System.out.println("+------------------------------+");
            System.out.println("| Enter your choice :          |");
            orderDetailsMenuChoice = sc.nextInt();
            System.out.println("+------------------------------+");
            if (orderDetailsMenuChoice == 1) {
                return;
            } else {
                try {
                    throw new ClickToCartException("Invalid Choice!");
                } catch (ClickToCartException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (orderDetailsMenuChoice != 1);
    }

    @Override
    public Order getOrderById(Integer id, Integer customerId) {
        final Customer customer = customerService.getCustomerById(customerId);

        for (Order order : customer.getOrders()) {
            if (Objects.equals(order.getId(), id))
                return order;
        }
        return null;
    }

}
