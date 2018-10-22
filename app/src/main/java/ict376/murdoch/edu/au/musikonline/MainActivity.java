package ict376.murdoch.edu.au.musikonline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private DataManager mDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById((R.id.fragement_container));

        mDataManager = new DataManager(getApplicationContext());

        if(fragment == null)
        {
            fragment = new LibraryFragment();
            fm.beginTransaction().add(R.id.fragement_container, fragment).commit();
        }
    }


}
