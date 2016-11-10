package chessen.productfinder.data.remote.model;

import android.support.annotation.Nullable;

/**
 * Created by carolinamarin on 11/6/16.
 */

public class Product {

    private Brand brand;
    private Image image;
    private String id;
    private String description;
    private String name;
    private String unbrandedName;
    private String url;
    private String price;
    private String salePrice;


    public Product() {

    }

    public Product(@Nullable String mid, @Nullable String mdescription, String mname, String murl, Brand mbrand, String mprice, Image mimage, String msalePrice, String munBrandedName) {
        id = mid;
        description = mdescription;
        name = mname;
        url = murl;
        brand = mbrand;
        price = mprice;
        image = mimage;
        salePrice = msalePrice;
        unbrandedName = munBrandedName;
    }

    public Product(String productId) {
        id = productId;
    }


    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getUrl() {
        return url;
    }


    @Nullable
    public Brand getBrand() {
        return brand;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public Image getImage() {
        return image;
    }

    @Nullable
    public String getPrice() {
        return price;
    }

    @Nullable
    public String getSalePrice() {
        return salePrice;
    }

    @Nullable
    public String getUnbrandedName() {
        return unbrandedName;
    }

}
