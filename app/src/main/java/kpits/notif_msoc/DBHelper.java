package kpits.notif_msoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notifmsoc.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DBContract.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL(DBContract.TABLE_DELETE);
        onCreate(db);
    }

    public Cursor getNotif(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + DBContract.Notif.TABLE_NAME + " WHERE " + DBContract.Notif.COLUMN_ID + " = " + id +"", null);
        return res;
    }

    public int numberOfNotifRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DBContract.Notif.TABLE_NAME);
        return numRows;
    }

    public boolean insertNotif(String content, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Notif.COLUMN_CONTENT, content);
        contentValues.put(DBContract.Notif.COLUMN_DATE, date);
        db.insert(DBContract.Notif.TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateNotif(Integer id, String content, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Notif.COLUMN_CONTENT, content);
        contentValues.put(DBContract.Notif.COLUMN_DATE, date);
        db.update(DBContract.Notif.TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DBContract.Notif.TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllNotifs() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        String query = "SELECT * FROM " + DBContract.Notif.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(query, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(DBContract.Notif.COLUMN_DATE)) + "\n"
                    + res.getString(res.getColumnIndex(DBContract.Notif.COLUMN_CONTENT)));
            res.moveToNext();
        }
        return array_list;
    }
}
