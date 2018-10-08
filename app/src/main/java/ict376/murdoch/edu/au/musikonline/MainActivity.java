package ict376.murdoch.edu.au.musikonline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById((R.id.fragement_container));

        if(fragment == null)
        {
            fragment = new LogInFragment();
            fm.beginTransaction().add(R.id.fragement_container, fragment).commit();
        }
    }
}
