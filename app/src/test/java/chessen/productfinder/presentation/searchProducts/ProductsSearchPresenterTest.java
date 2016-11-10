package chessen.productfinder.presentation.searchProducts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chessen.productfinder.data.ProductRepository;
import chessen.productfinder.data.remote.model.Brand;
import chessen.productfinder.data.remote.model.IPhoneSmall;
import chessen.productfinder.data.remote.model.Image;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.data.remote.model.ProductsList;
import chessen.productfinder.data.remote.model.Sizes;
import chessen.productfinder.presentation.core.CorePresenter;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carolinamarin on 11/8/16.
 */
public class ProductsSearchPresenterTest {


    @Mock
    ProductRepository productRepository;
    @Mock
    ProductsSearchContract.View view;

    ProductsSearchPresenter productSearchPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productSearchPresenter = new ProductsSearchPresenter(productRepository, Schedulers.immediate(), Schedulers.immediate());
        productSearchPresenter.addView(view);
    }

    @Test
    public void givenThereIsAValidSearchReturnResults() {
        //Arrange
        ProductsList productList = getDummyProductList();
        when(productRepository.searchProducts(anyString(), anyString(), anyInt())).thenReturn(Observable.<List<Product>>just(productList.getProducts()));

        //Act
        productSearchPresenter.search("");

        //Assert
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showSearchResults(productList.getProducts());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void givenThereisAnErrorThenShowErrorMessage() {
        //Act
        String errorMsg = "Network Error, Please try again later";
        when(productRepository.searchProducts(anyString(), anyString(), anyInt())).thenReturn(Observable.<List<Product>>error(new IOException(errorMsg)));

        //Arrange
        productSearchPresenter.search("test");

        //Assert
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showSearchResults(anyList());
        verify(view).showError(errorMsg);
    }

    @Test(expected = CorePresenter.MvpViewNotAddedViewException.class)
    public void givenThereIsNoViewAddedThrowsMvpException() {

        //Act
        productSearchPresenter.removeView();
        productSearchPresenter.search("test");

        //Assert
        verify(view, never()).showLoading();
        verify(view, never()).showSearchResults(anyList());
    }



    ProductsList getDummyProductList() {
        List<Product> productsList = new ArrayList<>();
        productsList.add(productOneDetails());
        productsList.add(productTwoDetails());
        return new ProductsList(productsList);
    }

    Product productOneDetails() {


        Brand brand = new Brand("brand name");
        IPhoneSmall iPhoneSmall = new IPhoneSmall("http://blalala.com/caro.jpg");
        Sizes sizes = new Sizes(iPhoneSmall);
        Image image = new Image(sizes);


        Product product = new Product("123", "sweater perfect for fall", "nice sweater", "url", brand, "30", image, "19", "");
        return product;
    }

    Product productTwoDetails() {


        Brand brand = new Brand("brand name");
        IPhoneSmall iPhoneSmall = new IPhoneSmall("http://blalala.com/caro.jpg");
        Sizes sizes = new Sizes(iPhoneSmall);
        Image image = new Image(sizes);

        Product product = new Product("123456", "sweater perfect for  summer", "nice swater", "url", brand, "25.36", image, "5.25", "");
        return product;
    }


}