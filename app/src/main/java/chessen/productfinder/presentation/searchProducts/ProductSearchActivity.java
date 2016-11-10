package chessen.productfinder.presentation.searchProducts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import chessen.productfinder.R;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.injection.Injection;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;




public class ProductSearchActivity extends AppCompatActivity implements ProductsSearchContract.View {

    private ProductsSearchContract.Presenter productSearchPresenter;
    private ProductsAdapter productsAdapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewProducts;
    private TextView textViewErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        productSearchPresenter = new ProductsSearchPresenter(Injection.provideProductRepo(), Schedulers.io(),
                AndroidSchedulers.mainThread());
        productSearchPresenter.addView(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textViewErrorMessage = (TextView) findViewById(R.id.text_view_error_msg);
        recyclerViewProducts = (RecyclerView) findViewById(R.id.recycler_view_products);
        productsAdapter = new ProductsAdapter(null, this);
        recyclerViewProducts.setAdapter(productsAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productSearchPresenter.removeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_product_search, menu);
        final MenuItem searchActionMenuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchTerm) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                productSearchPresenter.search(searchTerm);
                toolbar.setTitle(searchTerm);
                searchActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchActionMenuItem.expandActionView();
        return true;
    }

    @Override
    public void showSearchResults(List<Product> productList) {
        recyclerViewProducts.setVisibility(View.VISIBLE);
        textViewErrorMessage.setVisibility(View.GONE);
        productsAdapter.setItems(productList);
    }

    @Override
    public void showError(String message) {
        textViewErrorMessage.setVisibility(View.VISIBLE);
        recyclerViewProducts.setVisibility(View.GONE);
        textViewErrorMessage.setText(message);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewProducts.setVisibility(View.GONE);
        textViewErrorMessage.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerViewProducts.setVisibility(View.VISIBLE);
        textViewErrorMessage.setVisibility(View.GONE);

    }
}
