package service;

import model.Product;

public interface ProductService {
    void viewAllProducts(final Integer customerId);

    Product getProductById(final Integer id);
}
