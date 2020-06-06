package com.example.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "productTable")
class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo
    private String name;

    @ColumnInfo
    private boolean status;



    Product(@NonNull String name, boolean status){

        this.name = name;
        this.status = status;
    }

    public String getName(){
        return this.name;
    }

    long getId(){
        return this.id;
    }

    boolean getStatus(){
        return this.status;
    }

   void setId(long id){

        this.id = id;
   }

}
