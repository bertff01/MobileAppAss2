package ict376.murdoch.edu.au.mobileappass2attempt2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static ict376.murdoch.edu.au.mobileappass2attempt2.CustomerDBSchema.*;
import static ict376.murdoch.edu.au.mobileappass2attempt2.SongDBSchema.*;

/**
 * Refer to chapter 14 of the text for detail on many of these functions
 */
public class DataManager {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DataManager(Context context)
    {
        mContext = context;
        mDatabase = new myDBHelper(mContext).getWritableDatabase();
        initTables();
    }

    private void addCustomer(ict376.murdoch.edu.au.mobileappass2attempt2.Customer c)
    {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CustomerTable.NAME, null, values);


    }

    public void addCustomer(String username, String password)
    {
        String id =  Integer.toString(this.getCustomerCount() + 1);
        Customer temp = new Customer(id, username, "", password);
        this.addCustomer(temp);
    }

    public void addSong(Song s)
    {
        ContentValues values = getContentValues(s);

        mDatabase.insert(SongTable.NAME, null, values);
    }

    public void updateCustomer(ict376.murdoch.edu.au.mobileappass2attempt2.Customer c)
    {
        String id = c.getCust_id();
        ContentValues values = getContentValues(c);

        mDatabase.update(CustomerTable.NAME, values, CustomerTable.Cols.CUST_ID + " = ?",
                new String[] {id});
    }

    public void updateSong(Song s)
    {
        String id = s.getSong_id();
        ContentValues values = getContentValues(s);
        mDatabase.update(SongTable.NAME, values, SongTable.Cols.SONG_ID + " = /",
                new String[] {id});
    }

    private MyCursorWrapper queryCustomer(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                CustomerTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );

        return new MyCursorWrapper(cursor);
    }

    private MyCursorWrapper querySong(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                SongTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );

        return new MyCursorWrapper(cursor);
    }

    public int getCustomerCount()
    {
        MyCursorWrapper cursor = queryCustomer(null, null);

        return cursor.getCount();
    }
    //gets all customers
    public List<ict376.murdoch.edu.au.mobileappass2attempt2.Customer> getCustomers()
    {
        List<ict376.murdoch.edu.au.mobileappass2attempt2.Customer> customers = new ArrayList<>();

        MyCursorWrapper cursor = queryCustomer(null, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customers.add(cursor.getCustomer());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return customers;
    }
    //gets all songs
    public List<Song> getSongs()
    {
        List<Song> songs = new ArrayList<>();

        MyCursorWrapper cursor = querySong(null, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                songs.add(cursor.getSong());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return songs;
    }

    public ict376.murdoch.edu.au.mobileappass2attempt2.Customer getCustomer(String id)
    {
        MyCursorWrapper cursor = queryCustomer(CustomerTable.Cols.CUST_ID + " = ?",
                new String[] {id});

        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCustomer();

        }
        finally {
            cursor.close();
        }
    }

    //this is a private function used solely to initialize the content of the databases on creation
    private void initTables()
    {
        ict376.murdoch.edu.au.mobileappass2attempt2.Customer c = new ict376.murdoch.edu.au.mobileappass2attempt2.Customer();
        Song s = new Song();

        c.setCust_email("customer1@email.com");
        c.setCust_id("0001");
        c.setCust_username("customer1");
        c.setCust_password("customer1");

        s.setSong_id("0001");

        this.addCustomer(c);
        this.addSong(s);
    }
    private static ContentValues getContentValues(Song s)
    {
        ContentValues values = new ContentValues();
        values.put(SongTable.Cols.SONG_ID, s.getSong_id());

        return values;
    }

    private static ContentValues getContentValues(ict376.murdoch.edu.au.mobileappass2attempt2.Customer c)
    {
        ContentValues values = new ContentValues();
        values.put(CustomerTable.Cols.CUST_ID, c.getCust_id());
        values.put(CustomerTable.Cols.CUST_USERNAME, c.getCust_username());
        values.put(CustomerTable.Cols.CUST_PASSWORD, c.getCust_password());

        return values;
    }
}
