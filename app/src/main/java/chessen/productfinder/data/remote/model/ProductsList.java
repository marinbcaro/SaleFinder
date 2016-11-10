package chessen.productfinder.data.remote.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinamarin on 11/6/16.
 */

public class ProductsList {
    List<Product> products = new ArrayList<Product>();

    public ProductsList(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

}
