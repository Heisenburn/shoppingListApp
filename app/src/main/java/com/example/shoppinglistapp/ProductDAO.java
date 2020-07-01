package com.example.shoppinglistapp;

import androidx.constraintlayout.solver.Cache;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDAO {


    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCachedProduct(CachedProduct product);

    @Query("DELETE FROM productTable")
    void deleteAll();


    @Query("UPDATE SQLITE_SEQUENCE SET seq=0 WHERE NAME='productTable'")
    void restartId();

    @Query("DELETE FROM productTable WHERE name = :product")
    void deleteProduct(String product);


    @Query("SELECT * from productTable ORDER BY id ASC")
    LiveData<List<Product>> getProductsFromDatabase();

    @Query("SELECT * from CachedProductTable ORDER BY id DESC")
    LiveData<List<CachedProduct>> getCachedProductsFromDatabase();

    @Query("SELECT * from productTable")
    boolean hasAnyProducts();

    @Query("UPDATE productTable SET status = 0 where name =:product")
    void setProductFalseStatus(String product);


    @Query("SELECT COUNT() FROM productTable WHERE name ='First product'")
    boolean initialListPresent();

    @Query("SELECT COUNT() FROM productTable WHERE name =:product")
    boolean checkIfElementAlreadyInDataBase(String product);


}
