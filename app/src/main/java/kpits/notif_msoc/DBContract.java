package kpits.notif_msoc;

import android.provider.BaseColumns;

final class DBContract {

    public static final String TABLE_CREATE =
            "CREATE TABLE " + Notif.TABLE_NAME + "( "
                    + Notif.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Notif.COLUMN_CONTENT + " TEXT NOT NULL, "
                    + Notif.COLUMN_DATE + " TEXT NOT NULL"
                    + ");";

    public static final String TABLE_DELETE =
            "DROP TABLE IF EXISTS " + Notif.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Notif implements BaseColumns {
        public static final String TABLE_NAME = "NOTIFS";
        public static final String COLUMN_ID = "ID_NOTIF";
        public static final String COLUMN_CONTENT = "CONTENT_NOTIF";
        public static final String COLUMN_DATE = "DATE_NOTIF";
    }
}
