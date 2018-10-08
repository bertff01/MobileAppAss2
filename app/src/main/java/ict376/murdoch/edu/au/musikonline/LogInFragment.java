package ict376.murdoch.edu.au.musikonline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LogInFragment extends Fragment {

    private Button mSubmitButton;
    private EditText mUsername;
    private EditText mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.loginfragment, container, false);

        mSubmitButton = (Button) v.findViewById(R.id.loginButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //if login details are valid
                //replace with a function that checks a database and returns a boolean
                if(true)
                {
                    Fragment libraryFragment = new PlayingFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragement_container, libraryFragment);
                    ft.commit();
                }
                else
                {
                    //makeToast w/errorMessage
                }
            }
        });

        return v;
    }
}
