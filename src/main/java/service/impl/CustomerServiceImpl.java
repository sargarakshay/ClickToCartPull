package service.impl;

import exception.ClickToCartException;
import lombok.NoArgsConstructor;
import model.Customer;
import model.Order;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@NoArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final List<Customer> customers = new ArrayList<>();
    private static final ProductService productService = new ProductServiceImpl(true);
    private static final OrderService orderService = new OrderServiceImpl();
    private static final Scanner sc = new Scanner(System.in);

    public CustomerServiceImpl(boolean toInit) {
        if (toInit)
            init();
    }

    public void init() {
        customers.add(new Customer(1, "Ram", "ram", "ram@123"));
        customers.add(new Customer(2, "Shyam", "shyam", "shyam@456"));
        customers.add(new Customer(3, "Mohan", "mohan", "mohan@789"));
        customers.add(new Customer(4, "Raj", "raj", "raj@abc"));
        customers.add(new Customer(5, "Sita", "sita", "sita@def"));
        customers.add(new Customer(6, "Gita", "gita", "gita@ghi"));
        customers.add(new Customer(7, "Ramesh", "ramesh", "ramesh@jkl"));
        customers.add(new Customer(8, "Suresh", "suresh", "suresh@mno"));
        customers.add(new Customer(9, "Sujit", "sujit", "sujit@pqr"));
        customers.add(new Customer(10, "Vikram", "vikram", "vikram@stu"));
    }

    @Override
    public void registerNewCustomer() {
        boolean isSuccessful = false;
        do {
            try {
                System.out.println();
                System.out.println("+-------------------------------- +");
                System.out.println("|      Register New Customer      |");
                System.out.println("+---------------------------------+");
                System.out.println("| Hello..!!                       |");
                System.out.println("| Welcome To Click To Cart...     |");
                System.out.println("| Please fill out the form below. |");
                System.out.println("|                                 |");
                System.out.println("| Enter Name :                    |");
                String name = sc.nextLine();
                System.out.println("| Enter Username :                |");
                String username = sc.nextLine();
                System.out.println("| Enter Password :                |");
                String password = sc.nextLine();
                System.out.println("+---------------------------------+");

                Customer customer = new Customer(0, name, username, password);

                if (isCustomerValid(customer)) {
                    saveCustomer(customer);
                    isSuccessful = true;
                    System.out.println("Congratulations, " + customer.getName() + "! \nYou're now a registered customer. \nLet's take you to the login page.");
                    Thread.sleep(2000);
                    customerLogin();
                }
            } catch (InterruptedException | ClickToCartException e) {
                System.out.println(e.getMessage());
            }
        } while (!isSuccessful);
    }

    @Override
    public void customerLogin() {
        boolean isSuccessful = false;
        do {
            System.out.println();
            System.out.println("+-------------------------------- +");
            System.out.println("|        Customer Login           |");
            System.out.println("+--------------------------------+");
            System.out.println("| Welcome To Click To Cart...     |");
            System.out.println("| Please enter your details..     |");
            System.out.println("|                                 |");
            System.out.println("| Enter Username :                |");
            String username = sc.nextLine();
            System.out.println("| Enter Password :                |");
            String password = sc.nextLine();
            System.out.println("+---------------------------------+");
            try {
                Customer customer = this.getCustomerWithUsernameAndPassword(username, password);
                if (customer != null) {
                    customer.setIsCustomerSessionActive(true);
                    updateCustomer(customer);
                    isSuccessful = true;
                    Thread.sleep(1000);
                    System.out.println("Login successful!");
                    Thread.sleep(1000);
                    System.out.println("\nWelcome back, " + customer.getName() + "! \nWe're taking you to your dashboard now. Please wait a moment...");
                    Thread.sleep(1500);
                    customerDashboard(customer);
                    break;
                } else {
                    throw new ClickToCartException("Uh-oh! \nSomething went wrong with your login attempt. \nPlease check your username and password, and then try again :)");
                }
            } catch (ClickToCartException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } while (!isSuccessful);
    }

    @Override
    public void customerDashboard(Customer customer) {
        if (customer.getIsCustomerSessionActive()) {
            int choice;
            try {
                do {
                    Thread.sleep(1000);
                    System.out.println();
                    System.out.println("+------------------------------+");
                    System.out.println("|          Dashboard           |");
                    System.out.println("+------------------------------+");
                    System.out.println("| Hey!                         |");
                    System.out.println("| Welcome To Click To Cart!!   |");
                    System.out.println("|                              |");
                    System.out.println("| 1. My Account                |");
                    System.out.println("| 2. View Products             |");
                    System.out.println("| 3. My Orders                 |");
                    System.out.println("| 4. LogOut                    |");
                    System.out.println("+------------------------------+");
                    System.out.println("| Enter your choice :          |");
                    choice = sc.nextInt();
                    System.out.println("+------------------------------+");
                    switch (choice) {
                        case 1:
                            myAccount(customer);
                            break;
                        case 2:
                            productService.viewAllProducts(customer.getId());
                            break;
                        case 3:
                            myOrders(customer);
                            break;
                        case 4:
                            return;
                    }
                } while (choice != 5);
                customer.setIsCustomerSessionActive(false);
                updateCustomer(customer);
                System.out.println("Logout Successful!");
                System.out.println();
            } catch (ClickToCartException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void myOrders(Customer customer) {
        int myOrderMenuChoice;
        do {
            int index = 1;
            System.out.println();
            System.out.println("+------------------------------+");
            System.out.println("|           My Order           |");
            System.out.println("+------------------------------+");
            if (customer.getOrders().isEmpty()) {
                System.out.println("| No Orders Found!!            |");
                System.out.println("|                              |");
            } else {
                System.out.println("| #  Product Name              |");
                for (Order order : customer.getOrders()) {
                    System.out.println("| " + index++ + ". " + order.getProducts().get(0).getName());
                }
            }
            System.out.println("+------------------------------+");
            System.out.println("| 0. Goto Dashboard            |");
            System.out.println("+------------------------------+");
            System.out.println("| Enter your choice :          |");
            myOrderMenuChoice = sc.nextInt();
            System.out.println("+------------------------------+");

            if (myOrderMenuChoice == 0) {
                return;
            } else if (myOrderMenuChoice > 0 && myOrderMenuChoice <= customer.getOrders().size()) {
                orderService.viewOrderDetails(customer.getOrders().get(index - 2).getId(), customer.getId());
                return;
            } else {
                try {
                    throw new ClickToCartException("Invalid Choice!");
                } catch (ClickToCartException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (myOrderMenuChoice != 0);
    }

    @Override
    public void updateCustomer(Customer customerToBeUpdated) {
        int index = 0;
        for (Customer customer : customers) {
            if (Objects.equals(customer.getId(), customerToBeUpdated.getId())) {
                index = customers.indexOf(customer);
            }
        }

        customers.set(index, customerToBeUpdated);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        for (Customer customer : customers) {
            if (Objects.equals(customer.getId(), id))
                return customer;
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer getCustomerWithUsernameAndPassword(final String username, final String password) {
        for (Customer customer : getAllCustomers()) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void showAllCustomers() {
        int customersMenuChoice = 0;
        try {
            do {
                System.out.println();
                System.out.println("+------------------------------+");
                System.out.println("|           Customers          |");
                System.out.println("+------------------------------+");
                System.out.println("| #  Name                      |");
                for (Customer customer : customers) {
                    System.out.println("| " + customer.getId() + ". " + customer.getName());
                }
                System.out.println("+------------------------------+");
                System.out.println("| 0. Goto Dashboard            |");
                System.out.println("+------------------------------+");
                System.out.println("| Enter your choice :          |");
                customersMenuChoice = sc.nextInt();
                System.out.println("+------------------------------+");

                if (customersMenuChoice == 0) {
                    return;
                } else {
                    throw new ClickToCartException("Invalid Choice!");
                }
            } while (customersMenuChoice != 0);
        } catch (ClickToCartException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void myAccount(final Customer customer) throws ClickToCartException, InterruptedException {
        int myAccountMenuChoice;
        Thread.sleep(1000);
        System.out.println();
        System.out.println("+------------------------------+");
        System.out.println("|          My Account          |");
        System.out.println("+------------------------------+");
        System.out.println("| Hello!!, " + customer.getName());
        System.out.println("|                              |");
        System.out.println("|                              |");
        System.out.println("+------------------------------+");
        System.out.println("| 1. Goto Dashboard            |");
        System.out.println("| 2. LogOut                    |");
        System.out.println("+------------------------------+");
        System.out.println("| Enter your choice :          |");
        myAccountMenuChoice = sc.nextInt();
        System.out.println("+------------------------------+");

        switch (myAccountMenuChoice) {
            case 1:
                break;
            case 2:
                return;
            default:
                throw new ClickToCartException("Invalid Choice!");
        }
    }

    private void saveCustomer(final Customer customer) {
        customer.setId(customers.size() + 1);
        customers.add(customer);
    }

    private boolean isCustomerValid(Customer customer) throws ClickToCartException {
        String name = customer.getName();
        String username = customer.getUsername();
        String password = customer.getPassword();

        if (name == null || name.isEmpty()) {
            throw new ClickToCartException("Name cannot be empty");
        }

        if (username == null || username.isEmpty()) {
            throw new ClickToCartException("Username cannot be empty");
        }

        if (isUsernameAlreadyTaken(username)) {
            throw new ClickToCartException("Username already exists");
        }

        if (password == null || password.isEmpty()) {
            throw new ClickToCartException("Password cannot be empty");
        }

        if (!password.matches("(?=.*[A-Za-z])(?=.*\\d).{8,}")) {
            throw new ClickToCartException("Password must have at least eight characters,\n at least one letter and one number");
        }

        return true;
    }

    private boolean isUsernameAlreadyTaken(String username) {
        for (Customer customer : getAllCustomers()) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
}
