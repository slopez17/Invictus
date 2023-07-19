package co.com.uma.mseei.invictus.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Feedback is an entity class used to perform CRUD operations (Create, Read, Update, Delete) on table "feedbacks" in invictus_database.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
@Entity (tableName = "feedbacks",
        indices = {
            @Index(value = "id", unique = true)
        })
public class Feedback {
    @PrimaryKey
    @ColumnInfo (name = "id")
    private final int id;

    @ColumnInfo (name = "comments")
    private final String comments;

    public Feedback(int id, String comments) {
        this.id = id;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public String getComments() {
        return comments;
    }
}
