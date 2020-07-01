package com.example.shoppinglistapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CachedProductsAdapter extends RecyclerView.Adapter<CachedProductsAdapter.ProductViewHolder>{



    class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView productItemView;



        private ProductViewHolder(View itemView) {
            super(itemView);
            productItemView = itemView.findViewById(R.id.textView);


        }

    }



    private final LayoutInflater mInflater;
    private List<CachedProduct> mProducts; // Cached copy of products

    CachedProductsAdapter(Context context) {

        mInflater = LayoutInflater.from(context);


    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View itemView = mInflater.inflate(R.layout.recyclerview_item_last_added, parent, false);


        return new ProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        if (mProducts != null) {

            CachedProduct current = mProducts.get(position);

            holder.productItemView.setText(current.getName());



        } else {
            // Covers the case of data not being ready yet.
            holder.productItemView.setText("Loading...");
        }

        holder.productItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent("message_subject_intent");
                intent.putExtra("newProduct",String.valueOf(holder.productItemView.getText()));
                NewProductActivity.getContext().sendBroadcast(intent);

            }
        });
    }

    void setProducts(List<CachedProduct> products){
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
