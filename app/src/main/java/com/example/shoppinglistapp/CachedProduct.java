package com.example.shoppinglistapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CachedProductTable")
class CachedProduct {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo
    private String name;




    CachedProduct(@NonNull String name){

        this.name = name;

    }

    public String getName(){
        return this.name;
    }

    long getId(){
        return this.id;
    }



    void setId(long id){

        this.id = id;
    }

}
