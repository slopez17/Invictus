package co.com.uma.mseei.invictus.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

/**
 * Speed is an entity class used to perform CRUD operations (Create, Read, Update, Delete) on table "speeds" in invictus_database.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
@Entity (tableName = "speeds",
        primaryKeys = {"sport_id", "elapsed_time"},
        indices = {
            @Index(value = "sport_id")
        })
public class Speed {
    @ColumnInfo (name = "sport_id")
    private final int sportId;

    @NonNull
    @ColumnInfo (name = "elapsed_time")
    private final String elapsedTime;

    @ColumnInfo (name = "speed")
    private final float speed;

    public Speed(int sportId, @NonNull String elapsedTime, float speed) {
        this.sportId = sportId;
        this.elapsedTime = elapsedTime;
        this.speed = speed;
    }

    public int getSportId() {
        return sportId;
    }

    @NonNull
    public String getElapsedTime() {
        return elapsedTime;
    }

    public float getSpeed() {
        return speed;
    }
}
