package com.example.shoppinglistapp;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, CachedProduct.class}, version = 2,exportSchema = false)
public abstract class ProductsDataBase extends RoomDatabase {

    public abstract ProductDAO productDAO();



    private static volatile ProductsDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static ProductsDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductsDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductsDataBase.class, "product_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {


        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {


                ProductDAO dao = INSTANCE.productDAO();



                if(dao.hasAnyProducts() == false){

                    Product firstElement = new Product("First product",false);
                    Product secondElement = new Product("Second product",false);
                    Product thirdElement = new Product("Add yours!",false);
                    Product forthElement = new Product("It will disappear when you add yours",false);

                    dao.insert(firstElement);
                    dao.insert(secondElement);
                    dao.insert(thirdElement);
                    dao.insert(forthElement);
                }



            });
        }



    };



}




