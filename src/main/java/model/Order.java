package model;

import enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {

    private Integer id;
    private List<Product> products = new ArrayList<>();
    private OrderStatus status;
    private Customer customer;
}
