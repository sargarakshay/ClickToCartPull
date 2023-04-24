package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Customer {

    List<Order> orders = new ArrayList<>();
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Boolean isCustomerSessionActive = false;

    public Customer(Integer id, String name, String userName, String password) {
        this.id = id;
        this.name = name;
        this.username = userName;
        this.password = password;
    }
}
