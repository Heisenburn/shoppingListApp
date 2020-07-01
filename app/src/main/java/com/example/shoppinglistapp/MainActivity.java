package com.example.shoppinglistapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ProductViewModel mProductViewModel;

    public static final int NEW_PRODUCT_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ProductListAdapter adapter = new ProductListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                CheckBox checkBox = view.findViewById(R.id.checkBoxInProduct);
                String elementToBeMoved = Objects.requireNonNull(mProductViewModel.getAllProducts().getValue()).get(position).getName();


                if(checkBox.isChecked()){
                    checkBox.setChecked(false);

                    mProductViewModel.setFalseStatusInDataBase(elementToBeMoved);

                }else {

                    mProductViewModel.moveProductToTheBottom(elementToBeMoved);

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mProductViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {

                adapter.setProducts(products);



            }
        });

        Button removeAllButton = findViewById(R.id.removeAllButton);

        removeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductViewModel.removeAllElements();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                startActivityForResult(intent, NEW_PRODUCT_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String passedValue = Objects.requireNonNull(data.getStringExtra(NewProductActivity.EXTRA_REPLY));

            if(passedValue.contains(",")){


                String[] commaProducts = passedValue.split(",[ ]*");

                for (String commaProduct : commaProducts) {


                    Product product = new Product(commaProduct, false);

                    mProductViewModel.insert(product);



                }


            }else {


                Product product = new Product(Objects.requireNonNull(data.getStringExtra(NewProductActivity.EXTRA_REPLY)), false);
                mProductViewModel.insert(product);

            }
        }
    }
}
