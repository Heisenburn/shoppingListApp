package com.example.shoppinglistapp;

import android.app.Application;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

class ProductRepository {


    private ProductDAO productDAO;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<CachedProduct>> cachedProducts;

    ProductRepository(Application application) {
        ProductsDataBase db = ProductsDataBase.getDatabase(application);
        productDAO = db.productDAO();

        allProducts = productDAO.getProductsFromDatabase();
        cachedProducts = productDAO.getCachedProductsFromDatabase();

    }


    void moveProductToTheBottom(String product){
        ProductsDataBase.databaseWriteExecutor.execute(() -> {

            productDAO.deleteProduct(product);
            productDAO.insert(new Product(product,true));

        });
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }
    LiveData<List<CachedProduct>> getCachedProduct() {
        return cachedProducts;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Product product) {
        ProductsDataBase.databaseWriteExecutor.execute(() -> {

            if(productDAO.initialListPresent()){


                productDAO.deleteAll();
                productDAO.restartId();

                productDAO.insert(product);
                productDAO.insertCachedProduct(new CachedProduct(product.getName()));




            }

            else if(productDAO.checkIfElementAlreadyInDataBase(product.getName())){


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                NewProductActivity.getContext(),
                                product.getName()+" already added",
                                Toast.LENGTH_LONG).show();

                    }
                });




            }

            else{
                productDAO.insert(product);
                productDAO.insertCachedProduct(new CachedProduct(product.getName()));
            }
        });
    }

    void setFalseStatusInDataBase(String product){

        ProductsDataBase.databaseWriteExecutor.execute(() -> {

            productDAO.setProductFalseStatus(product);

        });
    }


    void removeAllElements() {

        ProductsDataBase.databaseWriteExecutor.execute(() -> {

            productDAO.deleteAll();
            productDAO.restartId();



        });




    }


}
