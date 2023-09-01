package co.com.uma.mseei.invictus.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * WeightRecord is an entity class used to perform CRUD operations (Create, Read, Update, Delete) on table "weights" in invictus_database.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
@Entity (tableName = "weights",
        indices = {
            @Index(value = "date", unique = true)
        })
public class Weight {
    @NonNull
    @PrimaryKey
    @ColumnInfo (name = "date")
    private final String date;

    @ColumnInfo (name = "weight")
    private final float weight;

    public Weight(@NonNull String date, float weight) {
        this.date = date;
        this.weight = weight;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public float getWeight() {
        return weight;
    }

}
