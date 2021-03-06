package chessen.productfinder.data;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import chessen.productfinder.data.remote.ProductSearchService;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.data.remote.model.ProductsList;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by carolinamarin on 11/6/16.
 */

public class ProductRepositoryImpl implements ProductRepository {


    private ProductSearchService searchService;


    public ProductRepositoryImpl(ProductSearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public Observable<List<Product>> searchProducts(final String category, final String searchTerm, final int offset) {

        //OPERATOR DEFER:do not create the Observable until a Subscriber subscribes; create a fresh Observable on each subscription
        return Observable.defer(new Func0<Observable<List<Product>>>() {
            @Override
            public Observable<List<Product>> call() {
                //CONCAT MAP: transform the items emitted by an Observable into Observables (or Iterables), then flatten this into a single Observable
                return (searchService.getProducts(category, searchTerm, offset)).concatMap(new Func1<ProductsList, Observable<? extends Product>>() {
                    @Override
                    public Observable<? extends Product> call(ProductsList productsList) {
                        //OPERATOR FROM: convert an Iterable, a Future, or an Array into an Observable
                        return (Observable.from(productsList.getProducts()));
                    }
                }).toList();
            }
        }).retryWhen(new RetryWithDelay(2, 3000));
    }

    public class RetryWithDelay implements
            Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.retryCount = 0;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> attempts) {
            return attempts
                    .concatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {

                            if (throwable instanceof IOException) {
                                if (++retryCount <= maxRetries) {
                                    return Observable.timer(retryDelayMillis,
                                            TimeUnit.MILLISECONDS);
                                }

                            }
                            return Observable.error(throwable);

                        }
                    });
        }
    }
}
