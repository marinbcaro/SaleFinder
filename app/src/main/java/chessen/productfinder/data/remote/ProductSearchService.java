package chessen.productfinder.data.remote;

import chessen.productfinder.data.remote.model.ProductsList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by carolinamarin on 11/6/16.
 */

public interface ProductSearchService {

    @GET("?sort=Popular&limit=10&fl=d0")
    Observable<ProductsList> getProducts(@Query("cat") String categoryId, @Query("fts") String search, @Query("offset") int offset);
}
