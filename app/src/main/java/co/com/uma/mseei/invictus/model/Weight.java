package co.com.uma.mseei.invictus.model;

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
    public String date;

    @ColumnInfo (name = "value")
    public float value;

}
