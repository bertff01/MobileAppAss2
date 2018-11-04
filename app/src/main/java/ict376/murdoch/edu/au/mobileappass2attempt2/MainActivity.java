package ict376.murdoch.edu.au.mobileappass2attempt2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * pretty boring but it just loads the first fragment
 */
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
            fragment = new LogInFragment();
            fm.beginTransaction().add(R.id.fragement_container, fragment).commit();
        }
    }


}
