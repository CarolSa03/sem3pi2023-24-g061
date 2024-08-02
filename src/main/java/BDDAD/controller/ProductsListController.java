package BDDAD.controller;

import BDDAD.dataAccess.repositories.ProductRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ProductsListController {
    private ProductRepository productRepository;
    public ProductsListController() {
        productRepository = getProductRepository();
    }
    private ProductRepository getProductRepository() {
        if (Objects.isNull(productRepository)) {
            Repositories repositories = Repositories.getInstance();
            productRepository = repositories.getProductRepository();
        }
        return productRepository;
    }

    public List<ProductDTO> getProducts() throws SQLException {
        return productRepository.getProducts();
    }

}
