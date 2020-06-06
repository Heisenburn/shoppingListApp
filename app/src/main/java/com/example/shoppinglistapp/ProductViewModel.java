package com.example.shoppinglistapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Product>> allProducts;

    public ProductViewModel(Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        allProducts = mRepository.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    void insert(Product product) {
        mRepository.insert(product);
    }

    void moveProductToTheBottom(String product){

        mRepository.moveProductToTheBottom(product);

    }





}
