package chessen.productfinder.presentation.searchProducts;

import java.util.List;

import chessen.productfinder.data.ProductRepository;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.presentation.core.CorePresenter;
import rx.Scheduler;
import rx.Subscriber;

/**
 * Created by carolinamarin on 11/7/16.
 */

class ProductsSearchPresenter extends CorePresenter<ProductsSearchContract.View> implements ProductsSearchContract.Presenter {
    private final Scheduler mainScheduler, ioScheduler;
    private ProductRepository mProductRepository;

    ProductsSearchPresenter(ProductRepository mProductRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.mProductRepository = mProductRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void search(String searchTerm) {
        checkViewAdded();
        getView().showLoading();

        addSubscription(mProductRepository.searchProducts("womens-clothes",searchTerm,0).subscribeOn(ioScheduler).observeOn(mainScheduler)
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError("Network Error, Please try again later");
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        getView().hideLoading();
                        getView().showSearchResults(products);
                    }
                }));
    }


}
