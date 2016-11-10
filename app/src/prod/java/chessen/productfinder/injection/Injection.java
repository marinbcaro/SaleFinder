package chessen.productfinder.injection;

import java.io.IOException;

import chessen.productfinder.data.ProductRepository;
import chessen.productfinder.data.ProductRepositoryImpl;
import chessen.productfinder.data.remote.ProductSearchService;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carolinamarin on 11/7/16.
 */
public class Injection {

    private static  String API_KEY="";
    private static final String BASE_URL = "http://api.shopstyle.com/api/v2/products/";
    private static OkHttpClient okHttpClient;
    private static ProductSearchService productSearchService;
    private static Retrofit retrofitInstance;


    public static ProductRepository provideProductRepo() {
        return new ProductRepositoryImpl(provideSearchRestService());
    }

    static ProductSearchService provideSearchRestService() {
        if (productSearchService == null) {
            productSearchService = getRetrofitInstance().create(ProductSearchService.class);
        }
        return productSearchService;
    }

    static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("pid", API_KEY)
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptor);
            builder.interceptors().add(logging);
            okHttpClient = builder.build();
        }

        return okHttpClient;
    }

    static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            Retrofit.Builder retrofit = new Retrofit.Builder().client(Injection.getOkHttpClient()).baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofitInstance = retrofit.build();

        }
        return retrofitInstance;
    }



}