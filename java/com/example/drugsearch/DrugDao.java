package com.example.drugsearch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrugDao {
    @Insert
    void insertDrug(Drug... drugs);

    @Query("SELECT * FROM drug")
    List<Drug> getAllDrugs();

    @Query("SELECT * FROM drug WHERE drug_name = :name")
    List<Drug> getDrugByName(String name);
}

