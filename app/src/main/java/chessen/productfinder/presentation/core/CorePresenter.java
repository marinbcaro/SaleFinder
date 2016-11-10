package chessen.productfinder.presentation.core;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by carolinamarin on 11/7/16.
 */

public class CorePresenter<T extends CoreMvpView> implements CoreMvpPresenter<T> {

    private T view;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();


    @Override
    public void addView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void removeView() {
        compositeSubscription.clear();
        view = null;
    }

    public T getView() {
        return view;
    }

    public void checkViewAdded() {
        if (!isViewAdded()) {
            throw new MvpViewNotAddedViewException();
        }
    }

    private boolean isViewAdded() {
        return view != null;
    }

    protected void addSubscription(Subscription subscription) {
        this.compositeSubscription.add(subscription);
    }

    public static class MvpViewNotAddedViewException extends RuntimeException {
        public MvpViewNotAddedViewException() {
            super("AddView method should be called");
        }
    }
}

