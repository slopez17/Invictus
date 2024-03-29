package co.com.uma.mseei.invictus.viewmodel.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.com.uma.mseei.invictus.model.database.Feedback;
import co.com.uma.mseei.invictus.model.database.Speed;
import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.model.database.Weight;

@Database(entities = {Weight.class, Speed.class, Sport.class, Feedback.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

        private static volatile AppDatabase INSTANCE;

        public static AppDatabase getDatabase(final Context context) {
                if (INSTANCE == null) {
                        synchronized (AppDatabase.class) {
                                if (INSTANCE == null) {
                                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                        AppDatabase.class, "invictus_database")
                                                // Wipes and rebuilds instead of migrating
                                                // if no Migration object.
                                                .fallbackToDestructiveMigration()
                                                .build();
                                }
                        }
                }
                return INSTANCE;
        }

        public abstract WeightDao weightDao();

        public abstract SpeedDao speedDao();

        public abstract SportDao sportDao();

        public abstract FeedbackDao feedbackDao();
}
