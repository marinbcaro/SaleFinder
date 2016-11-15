package chessen.productfinder.data.remote.model;

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
    private String priceLabel;
    private String salePriceLabel;


    public Product() {

    }

    public Product(String mid, String mdescription, String mname, String murl, Brand mbrand, String mprice, Image mimage, String msalePrice, String munBrandedName) {
        id = mid;
        description = mdescription;
        name = mname;
        url = murl;
        brand = mbrand;
        priceLabel = mprice;
        image = mimage;
        salePriceLabel = msalePrice;
        unbrandedName = munBrandedName;
    }

    public Product(String productId) {
        id = productId;
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getUrl() {
        return url;
    }



    public Brand getBrand() {
        return brand;
    }


    public String getDescription() {
        return description;
    }


    public Image getImage() {
        return image;
    }


    public String getPrice() {
        return price;
    }


    public String getSalePrice() {
        return salePrice;
    }

    public String getPriceLabel() {
        return priceLabel;
    }
    public String getSalePriceLabel() {
        return salePriceLabel;
    }

    public String getUnbrandedName() {
        return unbrandedName;
    }

}
