package chessen.productfinder.data.remote.model;

/**
 * Created by carolinamarin on 11/7/16.
 */
public class Image {
    private Sizes sizes;

    public Image(Sizes sizes){
        this.sizes=sizes;
    }

    public Sizes getSizes(){return sizes;}
}
