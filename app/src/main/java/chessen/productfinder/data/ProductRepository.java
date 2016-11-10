package chessen.productfinder.data;

import java.util.List;

import chessen.productfinder.data.remote.model.Product;
import rx.Observable;

/**
 * Created by carolinamarin on 11/6/16.
 */

public interface ProductRepository {
    Observable<List<Product>> searchProducts(String category,String searchTerm,int offset);
}
