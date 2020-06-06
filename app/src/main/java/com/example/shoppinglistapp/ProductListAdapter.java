package com.example.shoppinglistapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>{





    class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView productItemView;
        private CheckBox checkBox;


        private ProductViewHolder(View itemView) {
            super(itemView);
            productItemView = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkBoxInProduct);

        }

    }




    private final LayoutInflater mInflater;
    private List<Product> mProducts; // Cached copy of products

    ProductListAdapter(Context context) {

        mInflater = LayoutInflater.from(context);


    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);


        return new ProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        if (mProducts != null) {

            Product current = mProducts.get(position);

            holder.productItemView.setText(current.getName());



        } else {
            // Covers the case of data not being ready yet.
            holder.productItemView.setText("No Product");
        }
    }

    void setProducts(List<Product> products){
        mProducts = products;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mProducts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mProducts != null)
            return mProducts.size();
        else return 0;
    }


}
