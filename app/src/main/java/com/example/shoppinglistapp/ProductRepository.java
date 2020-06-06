package com.example.shoppinglistapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ProductRepository {

    private ProductDAO productDAO;
    private LiveData<List<Product>> allProducts;


    ProductRepository(Application application) {
        ProductsDataBase db = ProductsDataBase.getDatabase(application);
        productDAO = db.productDAO();

        allProducts = productDAO.getProductsFromDatabase();
    }


    void moveProductToTheBottom(String product){
        ProductsDataBase.databaseWriteExecutor.execute(() -> {

            productDAO.deleteProduct(product);
            productDAO.insert(new Product(product,true));

        });
    }

    boolean getProductStatus(String product){
        return productDAO.getProductStatus(product);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Product product) {
        ProductsDataBase.databaseWriteExecutor.execute(() -> {

            if(productDAO.initialListPresent()){

                productDAO.deleteAll();
                productDAO.insert(product);

            }else{
                productDAO.insert(product);

            }
        });
    }


}
