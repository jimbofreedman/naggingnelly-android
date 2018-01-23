package uk.co.ribot.androidboilerplate.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import uk.co.ribot.androidboilerplate.data.model.Action;
import uk.co.ribot.androidboilerplate.data.model.Folder;
import uk.co.ribot.androidboilerplate.data.model.Name;
import uk.co.ribot.androidboilerplate.data.model.Profile;

public class Db {

    public Db() { }

    public abstract static class RibotProfileTable {
        public static final String TABLE_NAME = "ribot_profile";

        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_HEX_COLOR = "hex_color";
        public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_BIO = "bio";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                        COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                        COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                        COLUMN_HEX_COLOR + " TEXT NOT NULL, " +
                        COLUMN_DATE_OF_BIRTH + " INTEGER NOT NULL, " +
                        COLUMN_AVATAR + " TEXT, " +
                        COLUMN_BIO + " TEXT" +
                " ); ";

        public static ContentValues toContentValues(Profile profile) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, profile.email());
            values.put(COLUMN_FIRST_NAME, profile.name().first());
            values.put(COLUMN_LAST_NAME, profile.name().last());
            values.put(COLUMN_HEX_COLOR, profile.hexColor());
            values.put(COLUMN_DATE_OF_BIRTH, profile.dateOfBirth().getTime());
            values.put(COLUMN_AVATAR, profile.avatar());
            if (profile.bio() != null) values.put(COLUMN_BIO, profile.bio());
            return values;
        }

        public static Profile parseCursor(Cursor cursor) {
            Name name = Name.create(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)));
            long dobTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH));

            return Profile.builder()
                    .setName(name)
                    .setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)))
                    .setHexColor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEX_COLOR)))
                    .setDateOfBirth(new Date(dobTime))
                    .setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR)))
                    .setBio(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIO)))
                    .build();
        }
    }

    public abstract static class FolderTable {
        public static final String TABLE_NAME = "gtd_folder";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL " +
                        " ); ";

        public static ContentValues toContentValues(Folder folder) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, folder.id());
            values.put(COLUMN_NAME, folder.name());
            return values;
        }

        public static Folder parseCursor(Cursor cursor) {
            return Folder.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
                    .build();
        }
    }

    public abstract static class ActionTable {
        public static final String TABLE_NAME = "gtd_action";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SHORT_DESCRIPTION = "name";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SHORT_DESCRIPTION + " TEXT NOT NULL," +
                        COLUMN_STATUS + " INTEGER NOT NULL," +
                        COLUMN_PRIORITY + " INTEGER NOT NULL" +
                        " ); ";

        public static ContentValues toContentValues(Action action) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, action.id());
            values.put(COLUMN_SHORT_DESCRIPTION, action.shortDescription());
            values.put(COLUMN_STATUS, action.status());
            values.put(COLUMN_PRIORITY, action.priority());
            return values;
        }

        public static Action parseCursor(Cursor cursor) {
            return Action.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setShortDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHORT_DESCRIPTION)))
                    .setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)))
                    .setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)))
                    .build();
        }
    }
}
