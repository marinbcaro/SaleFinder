package chessen.productfinder.injection;

import chessen.productfinder.data.ProductRepository;
import chessen.productfinder.data.ProductRepositoryImpl;
import chessen.productfinder.data.remote.MockProductSearchServiceImpl;
import chessen.productfinder.data.remote.ProductSearchService;

/**
 * Created by carolinamarin on 11/8/16.
 */

public class Injection {

    private static ProductSearchService productSearchService;


    public static ProductRepository provideProductRepo() {
        return new ProductRepositoryImpl(provideProductSearchRestService());
    }

    static ProductSearchService provideProductSearchRestService() {
        if (productSearchService == null) {
            productSearchService = new MockProductSearchServiceImpl();
        }
        return productSearchService;
    }
}
