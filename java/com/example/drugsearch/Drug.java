package com.example.drugsearch;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Drug {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "drug_name")
    public String drugName;

    @ColumnInfo(name = "drug_type")
    public String drugType;

    @ColumnInfo(name = "used_for")
    public String usedFor;

    @ColumnInfo(name = "potential_side_effects")
    public String potentialSideEffects;

    @ColumnInfo(name = "safe_during_pregnancy")
    public String safeDuringPregnancy;

    @ColumnInfo(name = "do_not_take_with")
    public String doNotTakeWith;

    @ColumnInfo(name = "nutrition_label")
    public String nutritionLabel;

    public Drug(String drugName, String drugType, String usedFor, String potentialSideEffects,
                String safeDuringPregnancy, String doNotTakeWith, String nutritionLabel) {
        this.drugName = drugName;
        this.drugType = drugType;
        this.usedFor = usedFor;
        this.potentialSideEffects = potentialSideEffects;
        this.safeDuringPregnancy = safeDuringPregnancy;
        this.doNotTakeWith = doNotTakeWith;
        this.nutritionLabel = nutritionLabel;
   }
    public String getDrugName() {
        return drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public String getUsedFor() {
        return usedFor;
    }

    public String getPotentialSideEffects() {
        return potentialSideEffects;
    }
    public String getSafeDuringPregnancy() {
        return safeDuringPregnancy;
    }
    public String getDoNotTakeWith() {
        return doNotTakeWith;
    }

    public String getNutritionLabel() {
        return nutritionLabel;
    }
}
