package chessen.productfinder.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chessen.productfinder.data.remote.ProductSearchService;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.data.remote.model.ProductsList;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carolinamarin on 11/7/16.
 */
public class ProductRepositoryImplTest {

    private static final String PRODUCT_ID_1 = "123";
    private static final String PRODUCT_ID_2 = "456";

    @Mock
    ProductSearchService searchService;

    private ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productRepository = new ProductRepositoryImpl(searchService);
    }


    @Test
    public void givenTheUserSearchForProductsThenServiceReturnsCorrectResults() {
        //Arrange
        when(searchService.getProducts(anyString(), anyString(), anyInt())).thenReturn(Observable.just(productsList()));

        //Act
        TestSubscriber<List<Product>> subscriber = new TestSubscriber<>();
        productRepository.searchProducts("", "", 0).subscribe(subscriber);


        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Product>> onNextEvents = subscriber.getOnNextEvents();
        List<Product> products = onNextEvents.get(0);

        //Assert
        Assert.assertEquals(PRODUCT_ID_1, products.get(0).getId());
        Assert.assertEquals(PRODUCT_ID_2, products.get(1).getId());
        verify(searchService).getProducts("", "", 0);

    }

    @Test
    public void givenTheUserSearchForProductsWhenIOExceptionThenSearchProductsRetried() {
        //Arrange
        when(searchService.getProducts(anyString(), anyString(), anyInt()))
                .thenReturn(getIOExceptionError(), Observable.just(productsList()));


        //Act
        TestSubscriber<List<Product>> subscriber = new TestSubscriber<>();
        productRepository.searchProducts("", "", 0).subscribe(subscriber);


        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        //Assert
        verify(searchService, times(2)).getProducts("", "", 0);
    }


    @Test
    public void givenTheUserSearchForProductsWhenOtherHttpErrorThenSearchTerminatedWithError() {
        //Arrange
        when(searchService.getProducts(anyString(), anyString(), anyInt())).thenReturn(get403ForbiddenError());
        TestSubscriber<List<Product>> subscriber = new TestSubscriber<>();

        //Act
        productRepository.searchProducts("", "", 0).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        //Assert
        verify(searchService).getProducts("", "", 0);
    }


    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

    private Observable<ProductsList> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }


    private ProductsList productsList() {
        Product product1 = new Product(PRODUCT_ID_1);

        Product product2 = new Product(PRODUCT_ID_2);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        ProductsList productsList = new ProductsList(products);
        return productsList;
    }


}