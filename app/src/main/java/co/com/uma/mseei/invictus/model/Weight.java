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

    @PrimaryKey
    @ColumnInfo (name = "date")
    @NonNull
    public String date;

    @ColumnInfo (name = "value")
    public float value;

    public Weight() {
    }

    public Weight(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
