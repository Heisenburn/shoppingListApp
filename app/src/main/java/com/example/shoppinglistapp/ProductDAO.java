package com.example.shoppinglistapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {


    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Query("DELETE FROM productTable")
    void deleteAll();

    @Query("DELETE FROM productTable WHERE name = :product")
    void deleteProduct(String product);

    @Query("SELECT * from productTable")
    LiveData<List<Product>> getProductsFromDatabase();

    @Query("SELECT * from productTable")
    boolean hasAnyProducts();

    @Query("SELECT status FROM productTable where name =:product")
    boolean getProductStatus(String product);


    @Query("SELECT COUNT() FROM productTable WHERE name ='Add yours!'")
    boolean initialListPresent();
}
