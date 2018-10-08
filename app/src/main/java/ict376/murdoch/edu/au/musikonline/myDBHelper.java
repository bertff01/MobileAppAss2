package ict376.murdoch.edu.au.musikonline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {

    public myDBHelper(Context context) {
        // Syntax: SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // The third argument is used to allow returning subclasses of Cursor when calling query
        super(context, "website" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        onCreate(db);
    }

}
