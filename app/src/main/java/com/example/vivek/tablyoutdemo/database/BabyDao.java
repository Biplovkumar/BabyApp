package com.example.vivek.tablyoutdemo.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vivek.tablyoutdemo.model.BabyName;

import java.util.List;

@Dao
public interface BabyDao {

    @Query("SELECT * FROM BabyTable ")
    LiveData<List<BabyName>> getAllData();

    @Query("SELECT * FROM BabyTable ")
    List<BabyName> getAllDataDirect();

    @Query("SELECT *FROM BabyTable WHERE Gender ='Boy' ")
    LiveData<List<BabyName>> getAllMaleName();

    @Query("SELECT *FROM BabyTable WHERE Gender ='Girl' ")
    LiveData<List<BabyName>> getAllFemaleName();


    @Insert
    void insert(BabyName babyName);

    @Insert
    void insertAll(List<BabyName> babyNames);

    @Delete
    void Delete(BabyName babyName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void Update(BabyName babyName);




}
