package ict376.murdoch.edu.au.musikonline;

import android.database.Cursor;

import static ict376.murdoch.edu.au.musikonline.CustomerDBSchema.CustomerTable.*;
import static ict376.murdoch.edu.au.musikonline.SongDBSchema.*;

public class MyCursorWrapper extends android.database.CursorWrapper {

    public MyCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Customer getCustomer()
    {
        String id = getString(getColumnIndex(Cols.CUST_ID));
        String username = getString(getColumnIndex(Cols.CUST_USERNAME));
        String password = getString(getColumnIndex(Cols.CUST_PASSWORD));

        Customer customer = new Customer(id, username, "", password);

        return customer;
    }

    public Song getSong()
    {
        String id = getString(getColumnIndex(SongTable.Cols.SONG_ID));

        Song song = new Song(id);

        return song;
    }
}
