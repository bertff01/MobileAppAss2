package ict376.murdoch.edu.au.mobileappass2attempt2;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;



public class LogInFragment extends Fragment {

    private Button mSubmitButton;
    private EditText mUsername;
    private EditText mPassword;
    private ict376.murdoch.edu.au.mobileappass2attempt2.DataManager mDataManager;
    private List<ict376.murdoch.edu.au.mobileappass2attempt2.Customer> mCustomerArrayList;
    boolean validLogin = false;
    private Button mCreateAccountButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.loginfragment, container, false);

        mUsername = (EditText) v.findViewById(R.id.usernameField);
        mPassword = (EditText) v.findViewById(R.id.passwordField);

        mDataManager = new ict376.murdoch.edu.au.mobileappass2attempt2.DataManager(getContext());

        mSubmitButton = (Button) v.findViewById(R.id.loginButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                String username =  mUsername.getText().toString();
                String password = mPassword.getText().toString();
                mCustomerArrayList = mDataManager.getCustomers();

                for(int i = 0; i < mCustomerArrayList.size(); i++)
                {
                    if(mCustomerArrayList.get(i).getCust_username().equals(username) &&
                            mCustomerArrayList.get(i).getCust_password().equals(username))
                    {
                        validLogin = true;
                    }
                }


                //if login details are valid
                //replace with a function that checks a database and returns a boolean
                if(validLogin)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    System.out.println("Login Successful");
                    Fragment libraryFragment = new ict376.murdoch.edu.au.mobileappass2attempt2.LibraryFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragement_container, libraryFragment);
                    ft.commit();
                }
                else
                {
                    //makeToast w/errorMessage
                    Toast.makeText(getActivity().getApplicationContext(), "Login Information Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCreateAccountButton = (Button) v.findViewById(R.id.createAccountButton);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Creating Account", Toast.LENGTH_SHORT).show();
                System.out.println("Create Account opened");
                Fragment createAccountFragment = new ict376.murdoch.edu.au.mobileappass2attempt2.CreateAccountFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragement_container, createAccountFragment);
                ft.commit();

            }
        });

        return v;
    }
}
