package chessen.productfinder.presentation.searchProducts;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import chessen.productfinder.R;

/**
 * Created by carolinamarin on 11/7/16.
 */

public class ProductsViewHolder extends  RecyclerView.ViewHolder {

    final TextView name;
    final ImageView image;
    final TextView price;
    final TextView salePrice;

    ProductsViewHolder(View v ) {
        super(v);
        name = (TextView) v.findViewById(R.id.product_name);
        image = (ImageView) v.findViewById(R.id.product_image);
        price = (TextView) v.findViewById(R.id.product_price);
        salePrice = (TextView) v.findViewById(R.id.product_salePrice);
        price.setPaintFlags(price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
