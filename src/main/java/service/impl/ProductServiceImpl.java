package service.impl;

import exception.ClickToCartException;
import lombok.NoArgsConstructor;
import model.Product;
import service.OrderService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


@NoArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final List<Product> products = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public ProductServiceImpl(final boolean toInit) {
        if (toInit)
            init();
    }

    private void init() {
        products.add(new Product(1, "T-Shirt", 600.0, null));
        products.add(new Product(2, "Saree", 500.0, null));
        products.add(new Product(3, "Kurta", 1000.0, null));
        products.add(new Product(4, "Lehenga", 3000.0, null));
        products.add(new Product(5, "Jhumka Earrings", 250.0, null));
        products.add(new Product(6, "Kolhapuri Sandals", 800.0, null));
        products.add(new Product(7, "Pashmina Shawl", 2000.0, null));
        products.add(new Product(8, "Bridal Jewelry Set", 5000.0, null));
        products.add(new Product(9, "Bandhani Saree", 1500.0, null));
        products.add(new Product(10, "Salwar Kameez", 1500.0, null));
        products.add(new Product(11, "Jute Bag", 300.0, null));
        products.add(new Product(12, "Chikankari Kurta", 1200.0, null));
        products.add(new Product(13, "Kalamkari Saree", 2000.0, null));
        products.add(new Product(14, "Phulkari Dupatta", 800.0, null));
        products.add(new Product(15, "Banarasi Silk Saree", 5000.0, null));
        products.add(new Product(16, "Kantha Stitch Saree", 1800.0, null));
        products.add(new Product(17, "Temple Jewelry Set", 4000.0, null));
        products.add(new Product(18, "Kashmiri Embroidered Shawl", 2500.0, null));
        products.add(new Product(19, "Madhubani Painted Saree", 2200.0, null));
        products.add(new Product(20, "Chanderi Silk Kurta", 1700.0, null));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public void viewAllProducts(final Integer customerId) {
        int productChoice;
        do {
            System.out.println();
            System.out.println("+------------------------------+");
            System.out.println("|          Products            |");
            System.out.println("+------------------------------+");
            System.out.println("| #  Product Name              |");
            for (Product product : products) {
                System.out.println("| " + product.getId() + ". " + product.getName());
            }
            System.out.println("+------------------------------+");
            System.out.println("| 0. Goto Dashboard            |");
            System.out.println("+------------------------------+");
            System.out.println("| Enter your choice :          |");
            productChoice = sc.nextInt();
            System.out.println("+------------------------------+");
            if (productChoice == 0) {
                break;
            } else {
                if (productChoice > 0 && productChoice <= products.size()) {
                    viewProduct(productChoice, customerId);
                    return;
                } else {
                    try {
                        throw new ClickToCartException("Invalid Choice");
                    } catch (ClickToCartException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } while (productChoice != 0);
    }

    public void viewProduct(final Integer productId, final Integer customerId) {
        final Product product = getProductById(productId);
        final OrderService orderService = new OrderServiceImpl();
        if (product != null) {
            int productChoice = 0;
            System.out.println();
            System.out.println("+------------------------------+");
            System.out.println("|          Product             |");
            System.out.println("+------------------------------+");
            System.out.println("| Product ID: " + product.getId());
            System.out.println("|                              |");
            System.out.println("| Name: " + product.getName());
            System.out.println("| Price: ₹" + product.getPrice());
            System.out.println("+------------------------------+");
            System.out.println("| 1. Order Now                 |");
            System.out.println("| 2. Product List              |");
            System.out.println("+------------------------------+");
            System.out.println("| Enter your choice :          |");
            productChoice = sc.nextInt();
            System.out.println("+------------------------------+");
            switch (productChoice) {
                case 1:
                    System.out.println("Order Successfully Placed");
                    System.out.println("Thank you for ordering.");
                    orderService.placeOrder(productId, customerId);
                    break;
                case 2:
                    return;
                default:
                    try {
                        throw new ClickToCartException("Invalid Choice!");
                    } catch (ClickToCartException e) {
                        System.out.println(e.getMessage());
                    }
            }
        }
    }

    @Override
    public Product getProductById(final Integer id) {
        for (Product product : products) {
            if (Objects.equals(product.getId(), id))
                return product;
        }

        return null;
    }
}
