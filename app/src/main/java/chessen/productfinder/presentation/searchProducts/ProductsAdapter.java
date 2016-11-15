package chessen.productfinder.presentation.searchProducts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import chessen.productfinder.R;
import chessen.productfinder.data.remote.model.IPhoneSmall;
import chessen.productfinder.data.remote.model.Image;
import chessen.productfinder.data.remote.model.Product;
import chessen.productfinder.data.remote.model.Sizes;

/**
 * Created by carolinamarin on 11/7/16.
 */

class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
    private final Context context;
    private List<Product> items;

    ProductsAdapter(List<Product> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ProductsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {

        Product item = items.get(position);

        holder.name.setText(item.getName());
        holder.price.setText(item.getPriceLabel());
        holder.salePrice.setText(item.getSalePriceLabel());
        Image ima = item.getImage();
        Sizes size = ima.getSizes();
        IPhoneSmall iphone = size.getIphone();
        Picasso.with(context).load(iphone.getUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    void setItems(List<Product> productList) {
        this.items = productList;
        notifyDataSetChanged();
    }
}