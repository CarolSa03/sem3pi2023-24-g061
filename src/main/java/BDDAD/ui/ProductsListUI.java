package BDDAD.ui;

import BDDAD.controller.ProductsListController;
import dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public class ProductsListUI implements Runnable {

    private ProductsListController controller;

    public ProductsListUI() {
        controller = new ProductsListController();
    }

    @Override
    public void run() {
        System.out.println("List of Products");
        System.out.println("\nID - Designation");
        try {
            controller.getProducts().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductDTO> getProducts() throws SQLException {
        return controller.getProducts();
    }

}
