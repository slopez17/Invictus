package co.com.uma.mseei.invictus.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Sport is an entity class used to perform CRUD operations (Create, Read, Update, Delete) on table "sports" in invictus_database.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
@Entity (tableName = "sports",
        indices = {
            @Index(value = "sport_id", unique = true)
        })
public class Sport implements Parcelable {

    @PrimaryKey
    @ColumnInfo (name = "sport_id")
    private final int sportId;

    @NonNull
    @ColumnInfo (name = "sport_type")
    private final String sportType;

    @NonNull
    @ColumnInfo (name = "start_date_time")
    private final String startDateTime;

    @NonNull
    @ColumnInfo (name = "end_date_time")
    private final String endDateTime;

    @NonNull
    @ColumnInfo (name = "elapsed_time")
    private final String elapsedTime;

    @ColumnInfo (name = "falls")
    private final int falls;

    @ColumnInfo (name = "steps")
    private final int steps;

    @ColumnInfo (name = "distance")
    private final float distance;

    @ColumnInfo (name = "calories")
    private final float calories;

    public Sport(int sportId, @NonNull String sportType, @NonNull String startDateTime, @NonNull String endDateTime, @NonNull String elapsedTime, int falls, int steps, float distance, float calories) {
        this.sportId = sportId;
        this.sportType = sportType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.elapsedTime = elapsedTime;
        this.falls = falls;
        this.steps = steps;
        this.distance = distance;
        this.calories = calories;
    }

    protected Sport(Parcel in) {
        sportId = in.readInt();
        sportType = in.readString();
        startDateTime = in.readString();
        endDateTime = in.readString();
        elapsedTime = in.readString();
        falls = in.readInt();
        steps = in.readInt();
        distance = in.readFloat();
        calories = in.readFloat();
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    public int getSportId() {
        return sportId;
    }

    @NonNull
    public String getSportType() {
        return sportType;
    }

    @NonNull
    public String getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public String getEndDateTime() {
        return endDateTime;
    }

    @NonNull
    public String getElapsedTime() {
        return elapsedTime;
    }

    public int getFalls() {
        return falls;
    }

    public int getSteps() {
        return steps;
    }

    public float getDistance() {
        return distance;
    }

    public float getCalories() {
        return calories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(sportId);
        parcel.writeString(sportType);
        parcel.writeString(startDateTime);
        parcel.writeString(endDateTime);
        parcel.writeString(elapsedTime);
        parcel.writeInt(falls);
        parcel.writeInt(steps);
        parcel.writeFloat(distance);
        parcel.writeFloat(calories);
    }
}
