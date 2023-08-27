package co.com.uma.mseei.invictus.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * FeedbackRecord is an entity class used to perform CRUD operations (Create, Read, Update, Delete) on table "feedbacks" in invictus_database.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
@Entity (tableName = "feedbacks",
        indices = {
            @Index(value = "sport_id", unique = true)
        })
public class Feedback {
    @PrimaryKey
    @ColumnInfo (name = "sport_id")
    private final int sportId;

    @ColumnInfo (name = "comments")
    private final String comments;

    public Feedback(int sportId, String comments) {
        this.sportId = sportId;
        this.comments = comments;
    }

    public int getSportId() {
        return sportId;
    }

    public String getComments() {
        return comments;
    }
}
