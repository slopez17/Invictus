package co.com.uma.mseei.invictus.model.database;

import static co.com.uma.mseei.invictus.util.MathOperations.kg2lbs;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Weight is an entity class used to perform CRUD operations (Create, Read, Update, Delete) on table "weights" in invictus_database.
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

    @ColumnInfo (name = "value")
    private final float value;

    public Weight(@NonNull String date, float value) {
        this.date = date;
        this.value = value;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public float getValue() {
        return value;
    }

    public float getValue(boolean isUnitSystemImperial) {
        return isUnitSystemImperial ? kg2lbs(value) : value;
    }
}
