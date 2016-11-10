package chessen.productfinder.presentation.searchProducts;

import java.util.List;

import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.presentation.core.CoreMvpPresenter;
import chessen.productfinder.presentation.core.CoreMvpView;

/**
 * Created by carolinamarin on 11/7/16.
 */

public interface ProductsSearchContract {
    interface View extends CoreMvpView {
        void showSearchResults(List<Product> productList);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends CoreMvpPresenter<View> {
        void search(String category);
    }
}
