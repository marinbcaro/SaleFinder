package chessen.productfinder.data.remote.model;

import android.support.annotation.Nullable;

/**
 * Created by carolinamarin on 11/7/16.
 */


public class Brand {
    private String name;

    public Brand(String name){
        this.name=name;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
