import exception.ClickToCartException;
import service.CustomerService;
import service.impl.CustomerServiceImpl;

import java.util.Scanner;

public class ClickToCart {
    public static void main(String[] args) {

        CustomerService customerService = new CustomerServiceImpl(true);

        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            try {
                System.out.println();
                System.out.println("+-------------------------------- +");
                System.out.println("|    Welcome To Click To Cart     |");
                System.out.println("+---------------------------------+");
                Thread.sleep(1000);
                System.out.println("|     Come for what you love.     |");
                Thread.sleep(1000);
                System.out.println("|   Stay for what you discover.   |");
                Thread.sleep(1000);
                System.out.println("|                                 |");
                System.out.println("| 1. New Customer Registration    |");
                System.out.println("| 2. Customer Login               |");
                System.out.println("| 3. All Customers                |");
                System.out.println("| 4. Exit                         |");
                System.out.println("+---------------------------------+");
                System.out.println("| Choose your option :            |");
                choice = Integer.parseInt(sc.nextLine());
                System.out.println("+---------------------------------+");

                switch (choice) {
                    case 1:
                        customerService.registerNewCustomer();
                        break;
                    case 2:
                        customerService.customerLogin();
                        break;
                    case 3:
                        customerService.showAllCustomers();
                        break;
                    case 4:
                        System.out.println("Oh no! You're leaving");
                        System.out.println("Bye!!");
                        System.exit(0);
                    default:
                        throw new ClickToCartException("Invalid Input : Please enter numbers between (1-3)...");
                }
            } catch (InterruptedException | ClickToCartException e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 4);
    }

}
