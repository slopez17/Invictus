package co.com.uma.mseei.invictus.util;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import co.com.uma.mseei.invictus.model.Weight;
import co.com.uma.mseei.invictus.viewmodel.WeightDao;

@Database(entities = {Weight.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
        public static final String DB_NAME = "invictus_database";
        public abstract WeightDao weightDao();
}
