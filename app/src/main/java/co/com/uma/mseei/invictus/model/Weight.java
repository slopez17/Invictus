package co.com.uma.mseei.invictus.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
}
