package chessen.productfinder.presentation.core;

/**
 * Created by carolinamarin on 11/7/16.
 */

public interface CoreMvpPresenter<V extends CoreMvpView> {

    void addView(V mvpView);

    void removeView();
}

