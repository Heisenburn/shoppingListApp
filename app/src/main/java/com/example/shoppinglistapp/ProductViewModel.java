package com.example.shoppinglistapp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

public class ProductViewModel extends AndroidViewModel{

    private ProductRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Product>> allProducts;
    private LiveData<List<CachedProduct>> cachedProducts;

    public ProductViewModel(Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        allProducts = mRepository.getAllProducts();
        cachedProducts = mRepository.getCachedProduct();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    LiveData<List<CachedProduct>> getCachedProducts() {
        return cachedProducts;
    }

    void insert(Product product) {
        mRepository.insert(product);
    }

    void moveProductToTheBottom(String product){

        mRepository.moveProductToTheBottom(product);

    }


    void setFalseStatusInDataBase(String product) {

        mRepository.setFalseStatusInDataBase(product);

    }

    void removeAllElements(){
        mRepository.removeAllElements();


    }



}
