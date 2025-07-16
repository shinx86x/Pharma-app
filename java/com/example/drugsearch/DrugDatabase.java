package com.example.drugsearch;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Drug.class}, version = 1)
public abstract class DrugDatabase extends RoomDatabase {
    public abstract DrugDao drugDao();

    private static DrugDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized DrugDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DrugDatabase.class, "drug-database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                DrugDao drugDao = instance.drugDao();
                drugDao.insertDrug(new Drug("Loperamide", "Opioid", "Treatment of diarrhea", "Dryness of mouth, dizziness, constipation", "Not recommended", "Escitalopram, Diphenhydramine, Acetaminophen", "loperamide_label"));
                drugDao.insertDrug(new Drug("Acetaminophen", "Analgesic", "Pain relief", "Swelling, vomiting, rashes", "Yes", "Isocarboxazid, Phenelzine, Tranylcypromine", "acetaminophen_label"));
                drugDao.insertDrug(new Drug("Ibuprofen", "Non-steroidal anti-inflammatory drug (NSAID)", "Pain relief", "Abdominal pain, headaches, drowsiness", "Not recommended", "Escitalopram, Naproxen, Aspirin","ibuprofen_label"));
                drugDao.insertDrug(new Drug("Aspirin", "Non-steroidal anti-inflammatory drug (NSAID)", "Pain relief", "Convulsions, Nausea, Swelling", "Not recommended", "Apixaban, Naproxen, Ibuprofen", "aspirin_label"));
                drugDao.insertDrug(new Drug("Dextromethorphan", " Antitussive", "Cough suppressant ", "Dizziness, Drowsiness, Nausea", "Yes", "Sertraline, Duloxetine, Ondansetron", "dextromethorphan_label"));
            });
        }
    };
}
