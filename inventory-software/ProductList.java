package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductList {

    private static ObservableList<Product> productList = FXCollections.observableArrayList();

    /**
     * Adds a product to the product list.
     * @param product
     */
    public static void addProduct(Product product){
        productList.add(product);
    }

    /**
     * Gets all of the products in the product list.
     * @return
     */
    public static ObservableList<Product> getProductList(){
        return productList;
    }

    /**
     * Removes a specific product in the product list.
     * @param product
     */
    public static void removeProduct(Product product) {
        productList.remove(product);
    }


}

