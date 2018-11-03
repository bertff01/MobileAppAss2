package ict376.murdoch.edu.au.mobileappass2attempt2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ict376.murdoch.edu.au.mobileappass2attempt2.SongDBSchema.*;
import static ict376.murdoch.edu.au.mobileappass2attempt2.CustomerDBSchema.*;


/********
 * Remember that the database will need to be updated whenever the structure is changed.
 * THis can be done with the onUpdate function, or just by modifying onCreate, and deleting the
 * database. See page 276 of the text for this.
 *
 *
 */
public class myDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public myDBHelper(Context context) {
        // Syntax: SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // The third argument is used to allow returning subclasses of Cursor when calling query
        super(context, DATABASE_NAME , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + CustomerTable.NAME + "(" +
        "_id integer primary key autoincrement, " +
        CustomerTable.Cols.CUST_ID + ", " +
        CustomerTable.Cols.CUST_USERNAME + ", " +
        CustomerTable.Cols.CUST_PASSWORD +
        ")");

        db.execSQL("create table " + SongTable.NAME + "(" +
        "_id integer primary key autoincrement, " +
        SongTable.Cols.SONG_ID +
        ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CustomerTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SongTable.NAME);
        onCreate(db);
    }

}
