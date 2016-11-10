package chessen.productfinder.data.remote;

import java.util.ArrayList;
import java.util.List;

import chessen.productfinder.data.remote.model.Brand;
import chessen.productfinder.data.remote.model.IPhoneSmall;
import chessen.productfinder.data.remote.model.Image;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.data.remote.model.ProductsList;
import chessen.productfinder.data.remote.model.Sizes;
import rx.Observable;

/**
 * Created by carolinamarin on 11/8/16.
 */

public class MockProductSearchServiceImpl implements ProductSearchService {


    private final List<Product> productList = new ArrayList<>();
    private static Observable dummySearchResult = null;


    public MockProductSearchServiceImpl() {


        Product firstProduct = createDummyProduct("123", "sweater perfect for fall", "Moto jacket", "https://img.shopstyle-cdn.com/mim/98/6a/986aa9c056af5605715a9489c73b6c42_small.jpg", null, "30.5", null, "19", "");
        Product secondProduct = createDummyProduct("123456", "sweater perfect for  summer", "Fall sweater", "https://img.shopstyle-cdn.com/mim/99/38/9938f97203282c399a4ffa70edb4e374_small.jpg", null, "25.26", null, "5.25", "");


        productList.add(firstProduct);
        productList.add(secondProduct);
    }

    public Product createDummyProduct(String mid, String mdescription, String mname, String murl, Brand mbrand, String mprice, Image mimage, String msalePrice, String munBrandedName) {

        Brand brand = new Brand("brand name");
        IPhoneSmall iPhoneSmall = new IPhoneSmall(murl);
        Sizes sizes = new Sizes(iPhoneSmall);
        Image image = new Image(sizes);

        Product product = new Product(mid, mdescription, mname, murl, brand, mprice, image, msalePrice, munBrandedName);
        return product;
    }


    public static void setDummySearchCallResult(Observable result) {
        dummySearchResult = result;
    }

    @Override
    public Observable<ProductsList> getProducts(final String category, final String searchTerm, final int offset) {
        if (dummySearchResult != null) {
            return dummySearchResult;
        }
        return Observable.just(new ProductsList(productList));
    }
}
