package com.example.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class NewProductActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private static EditText mEditWordView;




    private static Context context;

    private ProductViewModel mProductViewModel;

    public static Context getContext() {
        return NewProductActivity.context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_last_items);
        final CachedProductsAdapter adapter = new CachedProductsAdapter(this);
        recyclerView.setAdapter(adapter);


        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        recyclerView.setLayoutManager(layoutManager);

        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        mProductViewModel.getCachedProducts().observe(this, new Observer<List<CachedProduct>>() {
            @Override
            public void onChanged(@Nullable final List<CachedProduct> products) {

                adapter.setProducts(products);

            }
        });


        mEditWordView = findViewById(R.id.edit_word);
        final Button button = findViewById(R.id.button_save);


        NewProductActivity.context = getApplicationContext();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);

                }

                else {
                    String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);


                }

                finish();
            }
        });


        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() { //passing latelyAddedProducts to editText field
            @Override
            public void onReceive(Context context, Intent intent) {

                if (!mEditWordView.getText().toString().matches("")) { //if not empty

                    mEditWordView.append("," + intent.getStringExtra("newProduct"));

                } else {
                    mEditWordView.setText(intent.getStringExtra("newProduct"));
                }

            }

        };

        registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));
    }



}


