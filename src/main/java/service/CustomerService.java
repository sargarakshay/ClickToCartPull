package service;

import model.Customer;

import java.util.List;

public interface CustomerService {
    void customerLogin();
    void showAllCustomers();
    void registerNewCustomer();
    void customerDashboard(final Customer customer);
    void updateCustomer(final Customer customer);
    Customer getCustomerById(final Integer id);
    Customer getCustomerWithUsernameAndPassword(final String username, final String password);
    List<Customer> getAllCustomers();
}
